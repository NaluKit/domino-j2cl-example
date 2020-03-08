package org.dominokit.samples.ui.widget.attachment;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.upload.FileUpload;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.samples.Task;

import static org.jboss.elemento.Elements.em;
import static org.jboss.elemento.Elements.h;

public class FileUploadComponent
    extends BaseDominoElement<HTMLDivElement, FileUploadComponent> {
  
  private final FileUpload fileUpload = FileUpload.create();
  
  public FileUploadComponent(Task task) {
    fileUpload.multipleFiles()
              .setThumbSpans(4,
                             4,
                             4,
                             12,
                             12)
              .manualUpload()
              .accept("*/*")
              .appendChild(h(3).textContent("Drop files here or click to upload."))
              .appendChild(em().textContent("(This is just a demo upload. Selected files are not actually uploaded)"))
              .onAddFile(fileItem -> {
                DomGlobal.console.info("adding attachement");
                task.getAttachments()
                    .add(fileItem.getFile().name);
                fileItem.addRemoveHandler(file -> task.getAttachments()
                                                      .remove(file.name));
              });
    init(this);
  }
  
  public static FileUploadComponent create(Task task) {
    return new FileUploadComponent(task);
  }
  
  public FileUpload getFileUpload() {
    return fileUpload;
  }
  
  @Override
  public HTMLDivElement element() {
    return fileUpload.element();
  }
  
}
