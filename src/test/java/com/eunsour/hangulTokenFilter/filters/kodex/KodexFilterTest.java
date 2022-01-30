package com.eunsour.hangulTokenFilter.filters.kodex;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KodexFilterTest {

	private Analyzer analyzer;

	private String getKodexString(String text) throws IOException {
		TokenStream stream = analyzer.tokenStream("field", text);

		CharTermAttribute charAttr = stream.addAttribute(CharTermAttribute.class);

		stream.reset();

		List<String> tokenStrs = new ArrayList<>();
		while (stream.incrementToken()) {
			tokenStrs.add(charAttr.toString());
		}
		stream.close();

		String result = String.join(" ", tokenStrs);
		System.out.println(result);

		return result;
	}

	@BeforeEach
	public void setup() {
		analyzer = new Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
			@Override
			protected TokenStreamComponents createComponents(String fieldName) {
				Tokenizer tokenizer = new KeywordTokenizer();
				TokenStream tokenFilter = new KodexFilter(tokenizer);
				return new TokenStreamComponents(tokenizer, tokenFilter);
			}
		};
	}

	@Test
	void testContainsStacking() throws IOException {
		getKodexString("아이스");
		getKodexString("이니에스");
		getKodexString("페르메스");

		getKodexString("아우트");
		getKodexString("애플");
		getKodexString("은!숫!강!산!수#강#싼");

	}

//	@Test
//	void testOnlyHangul() throws IOException {
//		assertEquals("elasticsearch", getKodexString("딤ㄴ샻ㄴㄷㅁㄱ초"));
//	}
//
//	@Test
//	void testContainsEnglish() throws IOException {
//		assertEquals("google.com", getKodexString("해ㅐ힏.채ㅡ"));
//	}
//
//	@Test
//	void testContainsSpecialCharacters() throws IOException {
//		assertEquals("elasticsearch!@#$%^&&**((", getKodexString("딤ㄴ샻ㄴㄷㅁㄱ초!@#$%^&&**(("));
//	}

}
