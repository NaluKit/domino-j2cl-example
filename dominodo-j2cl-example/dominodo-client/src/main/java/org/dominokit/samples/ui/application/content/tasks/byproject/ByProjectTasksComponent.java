package org.dominokit.samples.ui.application.content.tasks.byproject;

import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksComponent;

public class ByProjectTasksComponent
    extends AbstractTasksComponent<IByProjectTasksComponent.Controller>
    implements IByProjectTasksComponent {
  
  public ByProjectTasksComponent() {
    super();
  }
  
  @Override
  public void showErrorMessage(String project) {
    MessageDialog.createMessage("Failed operation",
                                "Illegal project used: " + project,
                                () -> getController().doRouteHome())
                 .error()
                 .open();
  }
  
}
