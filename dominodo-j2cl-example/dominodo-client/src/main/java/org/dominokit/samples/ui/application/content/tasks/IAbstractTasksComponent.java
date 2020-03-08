package org.dominokit.samples.ui.application.content.tasks;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.dominokit.samples.Task;

import java.util.Date;
import java.util.List;

public interface IAbstractTasksComponent
    extends IsComponent<IAbstractTasksComponent.Controller, HTMLElement> {
  
  void edit(List<Task> models,
            boolean animate);
  
  interface Controller
      extends IsComponent.Controller {
    
    String doGetTitle();
    
    void doOnEditTask(Task task);
    
    void doOnResolved(Task task);
    
    void onTagSelected(Task task,
                       String tag);
    
    void doOnTaskDelete(Task task);
    
    void doOnTaskPriorityChange(Task task);
    
    void doOnUnResolved(Task task);
    
    void doOnUpdateDueDate(Task task,
                           Date date);
    
  }
  
}
