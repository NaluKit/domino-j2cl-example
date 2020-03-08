package org.dominokit.samples.ui.application.content.tasks.byproject;

import org.dominokit.samples.ui.application.content.tasks.IAbstractTasksComponent;

public interface IByProjectTasksComponent
    extends IAbstractTasksComponent {
  
  void showErrorMessage(String project);
  
  interface Controller
      extends IAbstractTasksComponent.Controller {
    
    void doRouteHome();
    
  }
  
}
