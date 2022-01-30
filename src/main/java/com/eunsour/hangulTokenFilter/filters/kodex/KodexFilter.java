package com.eunsour.hangulTokenFilter.filters.kodex;

import java.io.IOException;

import com.eunsour.hangulTokenFilter.hangul_util.KodexUtil;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class KodexFilter extends TokenFilter {

	private final CharTermAttribute charAttr;
	private final KodexUtil kodexUtil;

	public KodexFilter(TokenStream input) {
		super(input);
		kodexUtil = new KodexUtil();
		charAttr = addAttribute(CharTermAttribute.class);
	}

	@Override
	public final boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			String kodex = kodexUtil.decompose(charAttr.toString(), true);
			charAttr.setEmpty().append(kodex);
			return true;
		}

		return false;
	}
}
