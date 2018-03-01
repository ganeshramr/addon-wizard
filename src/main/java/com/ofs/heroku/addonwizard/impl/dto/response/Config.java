package com.ofs.heroku.addonwizard.impl.dto.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "BLOCKCHAIN_SERVICE_URL" })
public class Config {

	@JsonProperty("BLOCKCHAIN_SERVICE_URL")
	private String bLOCKCHAINSERVICEURL;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("BLOCKCHAIN_SERVICE_URL")
	public String getBLOCKCHAINSERVICEURL() {
		return bLOCKCHAINSERVICEURL;
	}

	@JsonProperty("SERVICE_URL")
	public void setBLOCKCHAINSERVICEURL(String sERVICEURL) {
		this.bLOCKCHAINSERVICEURL = sERVICEURL;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
