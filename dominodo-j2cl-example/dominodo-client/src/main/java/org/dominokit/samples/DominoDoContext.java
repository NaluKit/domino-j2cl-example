package org.dominokit.samples;

import com.github.nalukit.nalu.client.context.IsContext;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.samples.tasks.TasksRepository;

public class DominoDoContext
    implements IsContext {
  
  private String          version;
  private ColorScheme     colorScheme;
  private TasksRepository tasksRepository;
  
  public DominoDoContext() {
    this.colorScheme     = ColorScheme.INDIGO;
    this.tasksRepository = new TasksRepository();
    this.version         = "2.1.0-SNAPSHOT";
  }
  
  public String getVersion() {
    return version;
  }
  
  public ColorScheme getColorScheme() {
    return colorScheme;
  }
  
  public void setColorScheme(ColorScheme colorScheme) {
    this.colorScheme = colorScheme;
  }
  
  public TasksRepository getTasksRepository() {
    return tasksRepository;
  }
  
  public void setTasksRepository(TasksRepository tasksRepository) {
    this.tasksRepository = tasksRepository;
  }
  
}
