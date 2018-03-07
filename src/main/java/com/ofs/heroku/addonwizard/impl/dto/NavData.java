package com.ofs.heroku.addonwizard.impl.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "appname", "addon", "addons" })
public class NavData {

	@JsonProperty("appname")
	private String appname;
	@JsonProperty("addon")
	private String addon;
	@JsonProperty("addons")
	private List<Addon> addons = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("appname")
	public String getAppname() {
		return appname;
	}

	@JsonProperty("appname")
	public void setAppname(String appname) {
		this.appname = appname;
	}

	@JsonProperty("addon")
	public String getAddon() {
		return addon;
	}

	@JsonProperty("addon")
	public void setAddon(String addon) {
		this.addon = addon;
	}

	@JsonProperty("addons")
	public List<Addon> getAddons() {
		return addons;
	}

	@JsonProperty("addons")
	public void setAddons(List<Addon> addons) {
		this.addons = addons;
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
