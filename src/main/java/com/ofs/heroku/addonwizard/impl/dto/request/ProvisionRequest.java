package com.ofs.heroku.addonwizard.impl.dto.request;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ofs.heroku.addonwizard.impl.dto.OauthGrant;
import com.ofs.heroku.addonwizard.impl.dto.Options;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "callback_url", "heroku_id", "plan", "region",
		"oauth_grant", "logplex_token", "uuid", "options" })
public class ProvisionRequest {

	@JsonProperty("callback_url")
	private String callbackUrl;
	@JsonProperty("heroku_id")
	private String herokuId;
	@JsonProperty("plan")
	private String plan;
	@JsonProperty("region")
	private String region;
	@JsonProperty("oauth_grant")
	private OauthGrant oauthGrant;
	@JsonProperty("logplex_token")
	private String logplexToken;
	@JsonProperty("uuid")
	private String uuid;
	@JsonProperty("options")
	private Options options;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("callback_url")
	public String getCallbackUrl() {
		return callbackUrl;
	}

	@JsonProperty("callback_url")
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@JsonProperty("heroku_id")
	public String getHerokuId() {
		return herokuId;
	}

	@JsonProperty("heroku_id")
	public void setHerokuId(String herokuId) {
		this.herokuId = herokuId;
	}

	@JsonProperty("plan")
	public String getPlan() {
		return plan;
	}

	@JsonProperty("plan")
	public void setPlan(String plan) {
		this.plan = plan;
	}

	@JsonProperty("region")
	public String getRegion() {
		return region;
	}

	@JsonProperty("region")
	public void setRegion(String region) {
		this.region = region;
	}

	@JsonProperty("oauth_grant")
	public OauthGrant getOauthGrant() {
		return oauthGrant;
	}

	@JsonProperty("oauth_grant")
	public void setOauthGrant(OauthGrant oauthGrant) {
		this.oauthGrant = oauthGrant;
	}

	@JsonProperty("logplex_token")
	public String getLogplexToken() {
		return logplexToken;
	}

	@JsonProperty("logplex_token")
	public void setLogplexToken(String logplexToken) {
		this.logplexToken = logplexToken;
	}

	@JsonProperty("uuid")
	public String getUuid() {
		return uuid;
	}

	@JsonProperty("uuid")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@JsonProperty("options")
	public Options getOptions() {
		return options;
	}

	@JsonProperty("options")
	public void setOptions(Options options) {
		this.options = options;
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