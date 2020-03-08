package org.dominokit.samples.ui.application.content.tasks.bypriority;

import org.dominokit.samples.ui.application.content.tasks.IAbstractTasksComponent;

public interface IByPriorityTasksComponent
    extends IAbstractTasksComponent {
  
  void showErrorMessage(String priority);
  
  interface Controller
      extends IAbstractTasksComponent.Controller {
    
    void doRouteHome();
    
  }
  
}
