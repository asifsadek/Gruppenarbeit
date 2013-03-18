/*
 * Copyright 2007 Thomas Stock.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors:
 * 
 */
package hybridRep;

import java.util.Hashtable;

import net.sourceforge.jwbf.actions.Get;
import net.sourceforge.jwbf.actions.Post;
import net.sourceforge.jwbf.actions.mw.HttpAction;
import net.sourceforge.jwbf.actions.mw.MediaWiki;
import net.sourceforge.jwbf.actions.mw.util.MWAction;
import net.sourceforge.jwbf.actions.mw.util.ProcessException;
import net.sourceforge.jwbf.contentRep.mw.ContentAccessable;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * Writes an article.
 * 
 * 
 * TODO no api use.
 * @author Thomas Stock
 * @supportedBy MediaWiki 1.9.x, 1.10.x, 1.11.x, 1.12.x, 1.13.x, 1.14.x
 * 
 */
public class PostModifyContentH extends MWAction {

	private boolean first = true;
	private boolean second = true;
	private final Get g;
	private final ContentAccessable a;
	private static final Logger LOG = Logger.getLogger(PostModifyContentH.class);
	private Hashtable<String, String> tab = new Hashtable<String, String>();


	/**
	 * 
	 * @param a
	 *            the
	 */
	public PostModifyContentH(final ContentAccessable a) {
		this.a = a;
		String uS = "/index.php?title="
					+ MediaWiki.encode(a.getLabel())
					+ "&action=edit&dontcountme=s";
		g = new Get(uS);

	}

	
	public HttpAction getNextMessage() {
		if (first) {
			first = false;
			return g;
		}

		String uS = "";

		uS = "/index.php?title=" + MediaWiki.encode(a.getLabel())
				+ "&action=submit";

		Post pm = new Post(uS);
		pm.addParam("wpSave", "Save");

		pm.addParam("wpStarttime", tab.get("wpStarttime"));

		pm.addParam("wpEditToken", tab.get("wpEditToken"));

		pm.addParam("wpEdittime", tab.get("wpEdittime"));

		pm.addParam("wpTextbox1", a.getText());

		String editSummaryText = a.getEditSummary();
		if (editSummaryText != null && editSummaryText.length() > 200) {
			editSummaryText = editSummaryText.substring(0, 200);
		}

		pm.addParam("wpSummary", editSummaryText);
		if (a.isMinorEdit()) {

			pm.addParam("wpMinoredit", "1");

		}

		LOG.info("WRITE: " + a.getLabel());

		second = false;
		return pm;
	}

	@Override
	public boolean hasMoreMessages() {
		return first || second;
	}

	@Override
	public String processReturningText(String s, HttpAction hm)
			throws ProcessException {
		if (hm.getRequest().equals(g.getRequest())) {
			getWpValues(s, tab);
			LOG.debug(tab);
		}
		return s;
	}

	/**
	 * 
	 * @param text
	 *            where to search
	 * @param tab
	 *            tabel with required values
	 */
	private void getWpValues(final String text, Hashtable<String, String> tab) {

		String[] tParts = text.split("\n");
		// System.out.println(tParts.length);
		for (int i = 0; i < tParts.length; i++) {
			if (tParts[i].indexOf("wpEditToken") > 0) {
				// \<input type='hidden' value=\"(.*?)\" name=\"wpEditToken\"
				int begin = tParts[i].indexOf("value") + 7;
				int end = tParts[i].indexOf("name") - 2;
				// System.out.println(line.substring(begin, end));
				// System.out.println("read wp token:" + tParts[i]);
				tab.put("wpEditToken", tParts[i].substring(begin, end));

			} else if (tParts[i].indexOf("wpEdittime") > 0) {
				// value="(\d+)" name=["\']wpEdittime["\']
				int begin = tParts[i].indexOf("value") + 7;
				int end = tParts[i].indexOf("name") - 2;
				// System.out.println( "read wp edit: " +
				// tParts[i].substring(begin, end));

				tab.put("wpEdittime", tParts[i].substring(begin, end));

			} else if (tParts[i].indexOf("wpStarttime") > 0) {
				// value="(\d+)" name=["\']wpStarttime["\']
				int begin = tParts[i].indexOf("value") + 7;
				int end = tParts[i].indexOf("name") - 2;
				// System.out.println("read wp start:" + tParts[i]);

				tab.put("wpStarttime", tParts[i].substring(begin, end));

			}
		}

	}

}
