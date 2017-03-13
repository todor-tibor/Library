package rcplibrary;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.edu.library.model.Author;

import rcplibrary.controller.AuthorController;

public class SamplePart {

	private Text txtInput;
	private TableViewer tableViewer;

	@Inject
	private MDirtyable dirty;

	@PostConstruct
	public void createComposite(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		this.txtInput = new Text(parent, SWT.BORDER);
		this.txtInput.setMessage("Enter text to mark part as dirty");
		this.txtInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				SamplePart.this.dirty.setDirty(true);
			}
		});
		this.txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		AuthorController controller = new AuthorController();
		List<Author> authors = controller.getAll();
		this.tableViewer = new TableViewer(parent);
		for (Author a : authors) {
			this.tableViewer.add(a.toString());
		}
		this.tableViewer.add("Sample item 2");
		this.tableViewer.add("Sample item 3");
		this.tableViewer.add("Sample item 4");
		this.tableViewer.add("Sample item 5");
		this.tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@Focus
	public void setFocus() {
		this.tableViewer.getTable().setFocus();
	}

	@Persist
	public void save() {
		this.dirty.setDirty(false);
	}
}