package org.dominokit.samples.ui.application.content.tasks.resolved;

import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_RESOLVED,
            selector = "content",
            component = ResolvedTasksComponent.class,
            componentInterface = IResolvedTasksComponent.class)
public class ResolvedTaskController
    extends AbstractTasksController<IResolvedTasksComponent>
    implements IResolvedTasksComponent.Controller {
  
  public ResolvedTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .listResolved();
  }
  
  @Override
  public String doGetTitle() {
    return "Resolved Tasks";
  }
  
}
