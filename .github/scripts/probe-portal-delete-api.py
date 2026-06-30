#!/usr/bin/env python3
"""Probe Gradle Plugin Portal for a programmatic delete API (none is documented)."""

from __future__ import annotations

import json
import os
import sys
import urllib.parse

import requests
from requests_oauthlib import OAuth1

PORTAL_BASE = os.environ.get("PORTAL_BASE", "https://plugins.gradle.org")
PLUGIN_ID = "com.huanshankeji.git-version"
VERSION = "0.99.0-test2"

key = os.environ["GRADLE_PUBLISH_KEY"]
secret = os.environ["GRADLE_PUBLISH_SECRET"]
auth = OAuth1(key, client_secret=secret)

candidate_paths = [
    ("DELETE", f"/api/v1/publish/versions/{PLUGIN_ID}/{VERSION}"),
    ("DELETE", f"/api/v1/plugins/{PLUGIN_ID}/versions/{VERSION}"),
    ("DELETE", f"/api/v1/plugin/{PLUGIN_ID}/{VERSION}"),
    ("POST", f"/api/v1/publish/versions/delete/{PLUGIN_ID}"),
    ("POST", f"/api/v1/publish/versions/remove/{PLUGIN_ID}"),
    ("POST", f"/api/v1/publish/versions/delete"),
    ("POST", f"/api/v1/plugin/delete"),
]

payload = {"pluginId": PLUGIN_ID, "pluginVersion": VERSION, "version": VERSION}

deleted = False
for method, path in candidate_paths:
    url = f"{PORTAL_BASE}{path}"
    kwargs: dict = {"auth": auth, "timeout": 30}
    if method == "POST":
        kwargs["json"] = payload
        kwargs["headers"] = {"Content-Type": "application/json"}
    response = requests.request(method, url, **kwargs)
    print(f"{method} {url} -> {response.status_code}")
    if response.text.strip():
        body = response.text.strip().replace("\n", " ")
        print(f"  body: {body[:300]}")
    if response.status_code in (200, 204):
        deleted = True

print()
if deleted:
    print("At least one endpoint returned success; re-check portal metadata.")
    sys.exit(0)

print("No delete endpoint accepted the request.")
print("Use the portal UI (logged in as owner, within 7 days) or file a removal request:")
query = urllib.parse.urlencode(
    {
        "template": "60_plugin_removal.yml",
        "labels": "a:removal",
    }
)
print(f"https://github.com/gradle/plugin-portal-requests/issues/new?{query}")
sys.exit(1)
