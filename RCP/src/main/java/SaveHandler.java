
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SaveHandler {

	@CanExecute
	public boolean canExecute(final EPartService partService) {
		if (partService == null) {
			return false;
		} else {
			return true;
		}
	}

	@Execute
	public void execute(final EPartService partService) {
		partService.saveAll(false);
	}
}