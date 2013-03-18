package hybridRep;

import net.sourceforge.jwbf.actions.mw.util.ActionException;
import net.sourceforge.jwbf.actions.mw.util.ProcessException;
//import net.sourceforge.jwbf.bots.MediaWikiBotImpl;
import net.sourceforge.jwbf.contentRep.mw.ContentAccessable;


import hybrid.MediaWikiBot;

public class ArticleH extends net.sourceforge.jwbf.contentRep.mw.SimpleArticle {

	private final MediaWikiBot bot;
	
	public ArticleH(MediaWikiBot bot) {
		this.bot = bot;
	}

	public ArticleH(ContentAccessable ca, MediaWikiBot bot) {
		super(ca);
		this.bot = bot;
	}

	public ArticleH(String text, String label, MediaWikiBot bot) {
		super(text, label);
		this.bot = bot;
	}
	
	public void save() throws ActionException, ProcessException {
		bot.writeContent(this);
	}
	

}
