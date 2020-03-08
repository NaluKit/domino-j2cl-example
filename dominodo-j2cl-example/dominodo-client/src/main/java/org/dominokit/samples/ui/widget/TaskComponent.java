package org.dominokit.samples.ui.widget;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.Typography.Paragraph;
import org.dominokit.domino.ui.animations.Animation;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.cards.HeaderAction;
import org.dominokit.domino.ui.datepicker.DatePicker;
import org.dominokit.domino.ui.dialogs.ConfirmationDialog;
import org.dominokit.domino.ui.dropdown.DropDownMenu;
import org.dominokit.domino.ui.dropdown.DropDownPosition;
import org.dominokit.domino.ui.dropdown.DropdownAction;
import org.dominokit.domino.ui.icons.Icon;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.modals.BaseModal;
import org.dominokit.domino.ui.modals.ModalDialog;
import org.dominokit.domino.ui.notifications.Notification;
import org.dominokit.domino.ui.popover.Popover;
import org.dominokit.domino.ui.popover.PopupPosition;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.domino.ui.style.Styles;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.ScreenMedia;
import org.dominokit.samples.Priority;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.widget.attachment.AttachDialogComponent;
import org.dominokit.samples.ui.widget.attachment.AttachmentPanelComponent;
import org.dominokit.samples.ui.widget.attachment.FileUploadComponent;
import org.gwtproject.i18n.shared.cldr.DateTimeFormatInfo;
import org.gwtproject.i18n.shared.cldr.impl.DateTimeFormatInfo_factory;
import org.jboss.elemento.HtmlContentBuilder;

import java.util.Date;

import static org.jboss.elemento.Elements.hr;
import static org.jboss.elemento.Elements.small;

public class TaskComponent
    extends BaseDominoElement<HTMLDivElement, TaskComponent> {
  
  private final ColorScheme                     projectColor;
  private final Icon                            importantIcon;
  private       TaskComponentDelegate           delegate;
  private       HtmlContentBuilder<HTMLElement> dueDateElement;
  private       DateTimeFormatInfo              dateTimeFormatInfo = DateTimeFormatInfo_factory.create();
  private       Card                            card;
  private       Task                            task;
  private       DatePicker                      datePicker;
  private       Popover                         datePickerPopup;
  private       AttachmentPanelComponent        attachmentPanel;
  
  public TaskComponent(Task task,
                       TaskComponentDelegate delegate) {
    this.task     = task;
    this.delegate = delegate;
    
    projectColor   = ColorScheme.valueOf(task.getProject()
                                             .getColor());
    dueDateElement = small().textContent(formatDate(task.getDueDate()))
                            .css(Styles.pull_right,
                                 "due-date");
    
    datePicker = DatePicker.create()
                           .hideHeaderPanel()
                           .setDateTimeFormatInfo(dateTimeFormatInfo)
                           .setColorScheme(projectColor)
                           .hideCloseButton()
                           .hideClearButton()
    
                           .addDateDayClickHandler((date, dateTimeFormatInfo1) -> datePickerPopup.close())
                           .addDateSelectionHandler((date, dateTimeFormatInfo) -> {
                             dueDateElement.textContent(formatDate(date));
                             delegate.onUpdateDueDate(task,
                                                      date);
                             task.setDueDate(date);
                           });
    
    Badge projectName = Badge.create(task.getProject()
                                         .getName())
                             .setBackground(projectColor.color());
    
    importantIcon = Icons.ALL.priority_high()
                             .setColor(Color.RED)
                             .setTooltip("This task is important")
                             .styler(style1 -> style1.add(Styles.pull_right));
    //                             .collapse();
    
    attachmentPanel = AttachmentPanelComponent.create(task);
    
    card = Card.create(task.getTitle())
               .styler(style -> style.setProperty("border-left",
                                                  "5px solid " +
                                                  projectColor.color()
                                                              .getHex()))
               .appendDescriptionChild(projectName)
               .appendDescriptionChild(dueDateElement)
               .addHeaderAction(HeaderAction.create(Icons.ALL.more_vert())
                                            .hideOn(ScreenMedia.MEDIUM_AND_UP)
                                            .apply(element -> {
                                              DropDownMenu menu = createDropDownMenu(element);
                                              element.addClickListener(evt -> {
                                                evt.stopPropagation();
                                                menu.open();
                                              });
                                            }))
    
               .addHeaderAction(HeaderAction.create(Icons.ALL.priority_high()
                                                             .setTooltip("Toggle priority"))
                                            .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                            .addClickListener(evt -> {
                                              delegate.onTaskPriorityChange(task);
                                              update();
                                            }))
    
               .addHeaderAction(HeaderAction.create(Icons.ALL.delete()
                                                             .setTooltip("Delete task"))
                                            .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                            .addClickListener(evt -> showConfirmationDialog()))
    
               .addHeaderAction(HeaderAction.create(Icons.ALL.edit()
                                                             .setTooltip("Edit task"))
                                            .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                            .addClickListener(evt -> delegate.onEditTask(task)))
    
               .addHeaderAction(HeaderAction.create(Icons.ALL.attachment()
                                                             .setTooltip("Attach files"))
                                            .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                            .addClickListener(evt -> AttachDialogComponent.create(FileUploadComponent.create(task),
                                                                                                  this::update)
                                                                                          .open()))
    
               .addHeaderAction(HeaderAction.create(Icons.ALL.event()
                                                             .setTooltip("Pick Due date")
                                                             .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                                             .apply(dateIcon -> datePickerPopup = Popover.createPicker(dateIcon,
                                                                                                                       datePicker)
                                                                                                         .position(PopupPosition.TOP_DOWN)
                                                                                                         .styler(style -> style.setMaxWidth("300px"))))
                                            .addClickListener(evt -> datePickerPopup.show()))
               .addHeaderAction(getStatusAction(task))
    
               .appendChild(importantIcon)
               .appendChild(Paragraph.create(task.getDescription()))
               .appendChild(TagsPanelComponent.create(task,
                                                      delegate::onTagSelected))
               .appendChild(hr())
               .appendChild(attachmentPanel);
    
    init(this);
    update();
    
  }
  
  public static TaskComponent create(Task task,
                                     TaskComponentDelegate delegate) {
    return new TaskComponent(task,
                             delegate);
  }
  
  private DropDownMenu createDropDownMenu(HeaderAction element) {
    return DropDownMenu.create(element)
                       .setPosition(DropDownPosition.BOTTOM_LEFT)
                       .addAction(DropdownAction.create("Toggle priority")
                                                .addSelectionHandler(value -> delegate.onTaskPriorityChange(task)))
                       .addAction(DropdownAction.create("Delete")
                                                .addSelectionHandler(value -> showConfirmationDialog()))
                       .addAction(DropdownAction.create("Edit")
                                                .addSelectionHandler(value -> delegate.onEditTask(task)))
                       .addAction(DropdownAction.create("Attach")
                                                .addSelectionHandler(value -> AttachDialogComponent.create(FileUploadComponent.create(task),
                                                                                                           this::update)
                                                                                                   .open()))
                       .addAction(DropdownAction.create("Pick due date")
                                                .addSelectionHandler(value -> {
                                                  ModalDialog modal = datePicker.createModal("Duew date")
                                                                                .appendChild(datePicker)
                                                                                .open();
                                                  datePicker.addDateDayClickHandler((date, dateTimeFormatInfo1) -> {
                                                    modal.close();
                                                  });
                                                }))
                       .addAction(DropdownAction.create(task.isActive() ? "Resolve" : "Unresolve")
                                                .addSelectionHandler(value -> {
                                                  if (task.isActive()) {
                                                    resolve();
                                                  } else {
                                                    unresolve();
                                                  }
                                                }));
  }
  
  private void unresolve() {
    delegate.onUnResolve(task);
    update();
    Notification.createWarning("Oops! now You have more work to do. " + task.getTitle())
                .show();
  }
  
  private void resolve() {
    delegate.onResolved(task);
    update();
    Notification.createSuccess("Congrats! Task [" + task.getTitle() + "] have been resolved now.")
                .show();
  }
  
  private HeaderAction getStatusAction(Task task) {
    if (task.isActive()) {
      return HeaderAction.create(Icons.ALL.done_all()
                                          .setColor(Color.GREEN)
                                          .setTooltip("Resolve")
                                          .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                          .addClickListener(evt -> resolve()));
    } else {
      return HeaderAction.create(Icons.ALL.replay()
                                          .setColor(Color.ORANGE)
                                          .setTooltip("Unresolve")
                                          .hideOn(ScreenMedia.SMALL_AND_DOWN)
                                          .addClickListener(evt -> unresolve()));
    }
    
  }
  
  private ConfirmationDialog showConfirmationDialog() {
    return ConfirmationDialog.create("Confirm delete")
                             .appendChild(Paragraph.create("Are you sure you want to delete this task?"))
                             .apply(element -> element.getFooterElement()
                                                      .styler(style -> style.setBackgroundColor("#f3f3f3")))
                             .onConfirm(dialog -> {
                               dialog.close();
                               Animation.create(TaskComponent.this)
                                        .transition(Transition.LIGHT_SPEED_OUT)
                                        .duration(500)
                                        .callback(element1 -> {
                                          delegate.onTaskDelete(task);
                                        })
                                        .animate();
      
                             })
                             .onReject(BaseModal::close)
                             .open();
  }
  
  private void update() {
    importantIcon.toggleDisplay(Priority.IMPORTANT.equals(task.getPriority()));
    attachmentPanel.update();
  }
  
  private String formatDate(Date date) {
    return DatePicker.Formatter.getFormat(this.dateTimeFormatInfo.dateFormatFull(),
                                          this.dateTimeFormatInfo)
                               .format(date);
  }
  
  @Override
  public HTMLDivElement element() {
    return card.element();
  }
  
  public interface TaskComponentDelegate {
    
    void onResolved(Task task);
    
    void onEditTask(Task task);
    
    void onTagSelected(Task task,
                       String tag);
    
    void onTaskDelete(Task task);
    
    void onTaskPriorityChange(Task task);
    
    void onUnResolve(Task task);
    
    void onUpdateDueDate(Task task,
                         Date date);
    
  }
  
}
