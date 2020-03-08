package org.dominokit.samples.ui.widget;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.grid.flex.FlexLayout;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.samples.Task;

import static org.jboss.elemento.Elements.div;

public class TagsPanelComponent
    extends BaseDominoElement<HTMLDivElement, TagsPanelComponent> {
  
  private final FlexItem                   tagsContainer = FlexItem.create();
  private       HTMLDivElement             element       = div().element();
  private       Task                       task;
  private       TagsPanelComponentDelegate delegate;
  
  public TagsPanelComponent(Task task,
                            TagsPanelComponentDelegate delegate) {
    this.task     = task;
    this.delegate = delegate;
    
    element.appendChild(FlexLayout.create()
                                  .appendChild(tagsContainer)
                                  .element());
    init(this);
    update();
  }
  
  public static TagsPanelComponent create(Task task,
                                          TagsPanelComponentDelegate delegate) {
    return new TagsPanelComponent(task,
                                  delegate);
  }
  
  public void update() {
    ColorScheme projectColor = ColorScheme.valueOf(task.getProject()
                                                       .getColor());
    task.getTags()
        .forEach(tag -> tagsContainer.appendChild(Chip.create(tag)
                                                      .setTooltip("Click to search")
                                                      .addClickListener(evt -> delegate.onTagSelected(task,
                                                                                                      tag))
                                                      .addRemoveHandler(() -> task.getTags()
                                                                                  .remove(tag))
                                                      .setColorScheme(projectColor)));
  }
  
  @Override
  public HTMLDivElement element() {
    return element;
  }
  
  interface TagsPanelComponentDelegate {
    
    void onTagSelected(Task task,
                       String tag);
    
  }
  
}
