package org.dominokit.samples.ui.application.content.tasks.bypriority;

import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Priority;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_BY_PRIORITY,
            selector = "content",
            component = ByPriorityTasksComponent.class,
            componentInterface = IByPriorityTasksComponent.class)
public class ByPriorityTaskController
    extends AbstractTasksController<IByPriorityTasksComponent>
    implements IByPriorityTasksComponent.Controller {
  
  private Priority priority;
  
  public ByPriorityTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .listByPriority(this.priority);
  }
  
  @Override
  public String doGetTitle() {
    return "Tasks by Priority";
  }
  
  @AcceptParameter("priority")
  public void setPriority(String priority) {
    switch (priority) {
      case "IMPORTANT":
        this.priority = Priority.IMPORTANT;
        break;
      case "NORMAL":
        this.priority = Priority.NORMAL;
        break;
      default:
        this.component.showErrorMessage(priority);
    }
  }
  
  @Override
  public void doRouteHome() {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_ALL);
  }
  
}
