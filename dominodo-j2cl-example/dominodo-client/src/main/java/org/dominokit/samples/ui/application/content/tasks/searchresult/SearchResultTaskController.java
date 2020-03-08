package org.dominokit.samples.ui.application.content.tasks.searchresult;

import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_SEARCH_RESULT,
            selector = "content",
            component = SearchResultTasksComponent.class,
            componentInterface = ISearchResultTasksComponent.class)
public class SearchResultTaskController
    extends AbstractTasksController<ISearchResultTasksComponent>
    implements ISearchResultTasksComponent.Controller {
  
  private String searchtoken;
  
  public SearchResultTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .findTasks(this.searchtoken);
  }
  
  @Override
  public String doGetTitle() {
    return "Search Result";
  }
  
  @AcceptParameter("searchtoken")
  public void setProject(String searchtoken) {
    this.searchtoken = searchtoken;
  }
  
}
