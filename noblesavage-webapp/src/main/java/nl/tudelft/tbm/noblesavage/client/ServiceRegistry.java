package nl.tudelft.tbm.noblesavage.client;

import nl.tudelft.tbm.noblesavage.facade.ArticleFacade;
import nl.tudelft.tbm.noblesavage.facade.ArticleFacadeAsync;
import nl.tudelft.tbm.noblesavage.facade.CorpusFacade;
import nl.tudelft.tbm.noblesavage.facade.CorpusFacadeAsync;
import nl.tudelft.tbm.noblesavage.facade.UserFacade;
import nl.tudelft.tbm.noblesavage.facade.UserFacadeAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ServiceRegistry {

  private CorpusFacadeAsync corpusService;
  private ArticleFacadeAsync articleService;
  private UserFacadeAsync userService;
  
  public CorpusFacadeAsync getCorpusService() {
    if (corpusService == null) {
      corpusService = GWT.create(CorpusFacade.class);
      ((ServiceDefTarget) corpusService).setServiceEntryPoint(GWT.getModuleBaseURL() + "corpus.rpc");
    }
    return corpusService;
  }

  public ArticleFacadeAsync getArticleService() {
    if (articleService == null) {
      articleService = GWT.create(ArticleFacade.class);
      ((ServiceDefTarget) articleService).setServiceEntryPoint(GWT.getModuleBaseURL() + "article.rpc");
    }
    return articleService;
  }
  
  public UserFacadeAsync getUserService() {
    if (userService == null) {
    	userService = GWT.create(UserFacade.class);
      ((ServiceDefTarget) userService).setServiceEntryPoint(GWT.getModuleBaseURL() + "user.rpc");
    }
    return userService;
  }
}
