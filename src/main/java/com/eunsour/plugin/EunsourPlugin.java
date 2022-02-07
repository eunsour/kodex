package com.eunsour.hangulTokenFilter.plugin;

import java.util.HashMap;
import java.util.Map;

import com.eunsour.hangulTokenFilter.filters.kodex.KodexFilterFactory;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

public class EunsourPlugin extends Plugin implements AnalysisPlugin {

	@Override
	public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
		Map<String, AnalysisProvider<TokenFilterFactory>> extra = new HashMap<>();
		extra.put("eunsour_kodex", KodexFilterFactory::new);
		return extra;
	}
}
