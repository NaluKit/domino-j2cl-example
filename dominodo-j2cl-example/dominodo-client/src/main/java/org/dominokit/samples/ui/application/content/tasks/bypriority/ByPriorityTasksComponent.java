package org.dominokit.samples.ui.application.content.tasks.bypriority;

import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksComponent;

public class ByPriorityTasksComponent
    extends AbstractTasksComponent<IByPriorityTasksComponent.Controller>
    implements IByPriorityTasksComponent {
  
  public ByPriorityTasksComponent() {
    super();
  }
  
  @Override
  public void showErrorMessage(String priority) {
    MessageDialog.createMessage("Failed operation",
                                "Illegal priority used: " + priority,
                                () -> getController().doRouteHome())
                 .error()
                 .open();
  }
  
}
