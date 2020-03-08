package org.dominokit.samples.ui.application;

import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import elemental2.dom.DomGlobal;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.layout.TopBarAction;
import org.dominokit.domino.ui.search.Search;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.samples.Constants;
import org.dominokit.samples.DominoDoContext;
import org.dominokit.samples.DominoDoRoutes;

import static org.jboss.elemento.Elements.img;

/**
 * this is the presenter of the shell. The shell divides the browser in
 * severeal areas.
 */
@Shell("application")
public class ApplicationShell
    extends AbstractShell<DominoDoContext> {
  
  private Layout layout;
  
  public ApplicationShell() {
  }
  
  @Override
  public void attachShell() {
    
    Search search = Search.create()
                          .onSearch(this::onSearch);
    
    layout = Layout.create("DominoDo");
    layout.navigationBar(navigationBar -> navigationBar.insertBefore(search,
                                                                     layout.getNavigationBar()
                                                                           .firstChild()))
          .topBar(topBar -> topBar.appendChild(TopBarAction.create(Icons.ALL.settings())
                                                           .addClickListener(evt -> layout.showRightPanel()))
                                  .appendChild(TopBarAction.create(Icons.ALL.search())
                                                           .addClickListener(evt -> search.open())))
          .autoFixLeftPanel()
          .setLogo(img("/todo.png"))
          .show(ColorScheme.BLUE);
    
    layout.getLeftPanel()
          .setId("navigation");
    layout.getRightPanel()
          .setId("setttings");
    layout.getContentPanel()
          .appendChild(Row.create()
                          .appendChild(Column.span12()
                                             .setId("content")));
    
    Button addButton = Button.create(Icons.ALL.add())
                             .setBackground(Color.THEME)
                             .setContent("ADD TASK")
                             .styler(style -> style.add("add-button"))
                             .addClickListener(evt -> showAddDialog());
    
    DomGlobal.document.body.appendChild(addButton.element());
  }
  
  private void showAddDialog() {
    this.eventBus.fireEvent(ShowPopUpEvent.show("TaskEditor")
                                          .using("function",
                                                 Constants.FUNCTION_ADD)
                                          .using("id",
                                                 "-1"));
  }
  
  @Override
  public void onAttachedComponent() {
    DomGlobal.window.console.log("ApplicationShell: 'onAttachedComponent' called");
  }
  
  @Override
  public void detachShell() {
    layout.remove();
  }
  
  private void onSearch(String searchToken) {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_SEARCH_RESULT,
                      searchToken);
  }
  
}
