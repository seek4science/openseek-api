package org.fairdom.openseekapi.queries;

import java.util.Arrays;
import java.util.List;

import org.fairdom.openseekapi.general.AuthenticationException;
import org.json.simple.JSONObject;

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi;
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult;
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.SampleType;
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.fetchoptions.SampleTypeFetchOptions;
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.search.SampleTypeSearchCriteria;

public class SampleTypesFetcher{
	
	private static IApplicationServerApi as;
	private static String sessionToken;
	
	public void setOpenBisAccess(IApplicationServerApi as, String sessionToken) {
		SampleTypesFetcher.as = as;
		SampleTypesFetcher.sessionToken = sessionToken;
	}
	
	public List<SampleType> all() {

		SampleTypeFetchOptions fetchOptions = new SampleTypeFetchOptions();

		fetchOptions.withPropertyAssignments();

		SampleTypeSearchCriteria searchCriteria = new SampleTypeSearchCriteria();

		SearchResult<SampleType> types = as.searchSampleTypes(sessionToken, searchCriteria, fetchOptions);
		return types.getObjects();

	}

	public List<SampleType> byCodes(List<String> codes) {

		SampleTypeFetchOptions fetchOptions = new SampleTypeFetchOptions();

		fetchOptions.withPropertyAssignments();

		SampleTypeSearchCriteria searchCriteria = new SampleTypeSearchCriteria();

		searchCriteria.withCodes().thatIn(codes);

		SearchResult<SampleType> types = as.searchSampleTypes(sessionToken, searchCriteria, fetchOptions);
		return types.getObjects();
	}

	public List<SampleType> byCode(String code) {

		SampleTypeFetchOptions fetchOptions = new SampleTypeFetchOptions();

		fetchOptions.withPropertyAssignments();

		SampleTypeSearchCriteria searchCriteria = new SampleTypeSearchCriteria();

		if (code.contains(",")) {
			List<String> codes = Arrays.asList(code.split(","));
			searchCriteria.withCodes().thatIn(codes);
		} else {
			searchCriteria.withCode().thatEquals(code);
		}

		SearchResult<SampleType> types = as.searchSampleTypes(sessionToken, searchCriteria, fetchOptions);
		return types.getObjects();
	}

	public List<SampleType> bySemantic(JSONObject query) throws AuthenticationException {
		throw new UnsupportedOperationException("Semantic annotations are not available in official release");
	}

}
