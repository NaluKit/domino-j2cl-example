package org.dominokit.samples;

public class DominoDoRoutes {
  
  public final static String ROUTE_TASKS_ALL           = "/application/tasks/all";
  public final static String ROUTE_TASKS_BY_PROJECT    = "/application/tasks/project/:project";
  public final static String ROUTE_TASKS_BY_PRIORITY   = "/application/tasks/priority/:priority";
  public final static String ROUTE_TASKS_NEXT_WEEK     = "/application/tasks/nextweek";
  public final static String ROUTE_TASKS_RESOLVED      = "/application/tasks/resolved";
  public final static String ROUTE_TASKS_SEARCH_RESULT = "/application/tasks/result/:searchtoken";
  public final static String ROUTE_TASKS_TODAY         = "/application/tasks/today";
  public final static String ROUTE_ERROR               = "/error/show";
  
  @SuppressWarnings("unused")
  private DominoDoRoutes() {
  }
  
}

