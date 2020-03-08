package org.dominokit.samples.ui.application.content.tasks.all;

import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_ALL,
            selector = "content",
            component = AllTasksComponent.class,
            componentInterface = IAllTasksComponent.class)
public class AllTaskController
    extends AbstractTasksController<IAllTasksComponent>
    implements IAllTasksComponent.Controller {
  
  public AllTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .listAll();
  }
  
  @Override
  public String doGetTitle() {
    return "All Tasks";
  }
  
}
