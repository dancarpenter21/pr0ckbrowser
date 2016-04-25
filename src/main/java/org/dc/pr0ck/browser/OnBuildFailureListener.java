package org.dc.pr0ck.browser;

@FunctionalInterface
public interface OnBuildFailureListener {

	void onFailure(Exception e);
}
