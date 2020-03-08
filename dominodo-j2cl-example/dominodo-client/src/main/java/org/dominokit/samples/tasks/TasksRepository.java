package org.dominokit.samples.tasks;

import elemental2.core.JsDate;
import org.dominokit.samples.Priority;
import org.dominokit.samples.Project;
import org.dominokit.samples.Task;

import java.util.*;
import java.util.stream.Collectors;

public class TasksRepository {
  
  public static final int                  _1_day   = 24 * 60 * 60 * 1000;
  public final static Map<String, Project> PROJECTS = new HashMap<>();
  private             List<Task>           tasks    = new ArrayList<>();
  
  public List<Task> listAll() {
    return tasks.stream()
                .filter(Task::isActive)
                .collect(Collectors.toList());
  }
  
  public List<Task> listByProjectName(String projectName) {
    return tasks.stream()
                .filter(task -> task.getProject()
                                    .getName()
                                    .equals(projectName) && task.isActive())
                .collect(Collectors.toList());
  }
  
  public List<Task> listByPriority(Priority priority) {
    return tasks.stream()
                .filter(task -> task.getPriority()
                                    .equals(priority) && task.isActive())
                .collect(Collectors.toList());
  }
  
  public List<Task> listTodayTasks() {
    return tasks.stream()
                .filter(task -> {
                  JsDate todayDate = new JsDate();
                  JsDate taskDate = new JsDate((double) task.getDueDate()
                                                            .getTime());
                  return todayDate.getYear() == taskDate.getYear()
                         && todayDate.getMonth() == taskDate.getMonth()
                         && todayDate.getDate() == taskDate.getDate()
                         && task.isActive();
      
                })
                .collect(Collectors.toList());
  }
  
  public List<Task> listNextWeekTasks() {
    return tasks.stream()
                .filter(task -> {
                  JsDate todayDate = new JsDate();
                  JsDate taskDate = new JsDate((double) task.getDueDate()
                                                            .getTime());
                  double diff = taskDate.getTime() - todayDate.getTime();
                  return diff > 0
                         && diff <= 604800000
                         && task.isActive();
      
                })
                .collect(Collectors.toList());
  }
  
  public List<Task> listResolved() {
    return tasks.stream()
                .filter(task -> !task.isActive())
                .collect(Collectors.toList());
  }
  
  public void addTask(Task task) {
    tasks.add(task);
  }
  
  public void removeTask(Task task) {
    tasks.remove(task);
  }
  
  public List<Task> findByTag(String tag) {
    return tasks.stream()
                .filter(task -> task.isActive() && task.getTags()
                                                       .stream()
                                                       .anyMatch(s -> s.toLowerCase()
                                                                       .contains(tag.toLowerCase())))
                .collect(Collectors.toList());
  }
  
  public Task findById(String id) {
    Optional<Task> optional = tasks.stream()
                                   .filter(task -> id.equals(task.getId()))
                                   .findFirst();
    return optional.orElse(null);
  }
  
  public List<Task> findTasks(String searchText) {
    return tasks.stream()
                .filter(task -> task.isActive() && (task.getTitle()
                                                        .contains(searchText.toLowerCase())
                                                    || task.getDescription()
                                                           .toLowerCase()
                                                           .contains(searchText.toLowerCase())
                                                    || task.getProject()
                                                           .getName()
                                                           .toLowerCase()
                                                           .contains(searchText.toLowerCase())
                                                    || task.getTags()
                                                           .stream()
                                                           .anyMatch(tag -> tag.toLowerCase()
                                                                               .contains(searchText.toLowerCase()))))
                .collect(Collectors.toList());
  }
  
  public void updateTask(Task task) {
    if (!Objects.isNull(task)) {
      tasks.forEach(m -> {
        if (m.getId()
             .equals(task.getId())) {
          m.setStatus(task.getStatus());
          m.setAttachments(task.getAttachments());
          m.setDescription(task.getDescription());
          m.setDueDate(task.getDueDate());
          m.setPriority(task.getPriority());
          m.setProject(task.getProject());
          m.setTags(task.getTags());
          m.setTitle(task.getTitle());
        }
      });
    }
  }
  
  public boolean isValidProjectName(String projectName) {
    return PROJECTS
        .containsKey(projectName);
  }
  
}