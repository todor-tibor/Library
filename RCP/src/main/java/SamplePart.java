
import java.util.Arrays;
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

		this.tableViewer = new TableViewer(parent);

		// this.tableViewer.setContentProvider(ArrayContentProvider));
		this.tableViewer.setInput(createInitialDataModel());
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

	private List<String> createInitialDataModel() {
		return Arrays.asList("Sample item 1", "Sample item 2", "Sample item 3", "Sample item 4", "Sample item 5");
	}
}