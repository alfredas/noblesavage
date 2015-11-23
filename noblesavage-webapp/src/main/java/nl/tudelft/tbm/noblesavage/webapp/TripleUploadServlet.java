package nl.tudelft.tbm.noblesavage.webapp;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

public class TripleUploadServlet extends UploadAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
        String response = "";
        int count = 0;
        for (FileItem item : sessionFiles) {
            if (false == item.isFormField()) {
                count++;
                try {
                    Date date = new Date();
                    String relativePath = date.getTime() + "/" + item.getName();
                    File dir = new File(getServletContext().getInitParameter("projectFolder") + date.getTime());
                    dir.mkdirs();
                    String filePath = getServletContext().getInitParameter("projectFolder") + relativePath;
                    File file = new File(filePath);
                    item.write(file);
                    response += "<file-" + count + "-field>" + item.getFieldName() + "</file-" + count + "-field>\n";
                    response += "<file-" + count + "-name>" + item.getName() + "</file-" + count + "-name>\n";
                    response += "<file-" + count + "-size>" + item.getSize() + "</file-" + count + "-size>\n";
                    response += "<file-" + count + "-type>" + item.getContentType() + "</file-" + count + "-type>\n";
                    response += "<file-" + count + "-path>" + relativePath + "</file-" + count + "-path>\n";
                } catch (Exception e) {
                    throw new UploadActionException(e);
                }
            }
        }

        removeSessionFileItems(request);

        return "<response>\n" + response + "</response>\n";
    }

}