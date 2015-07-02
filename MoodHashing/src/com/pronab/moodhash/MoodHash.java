package com.pronab.moodhash;

import android.app.Application;
 public  class  MoodHash extends Application {
	                            public String photostore ="/DCIM/CAMERA/" ;
	                            private String lastVerb;
                                private String user =  " ";
                                private String pwd ;
                                public String getCurrentmood() {
									return currentmood;
								}
								public void setCurrentmood(String currentmood) {
									this.currentmood = currentmood;
								}
								public String getCurrenttrail() {
									return currenttrail;
								}
								public void setCurrenttrail(String currenttrail) {
									this.currenttrail = currenttrail;
								}
								private String url;
                                private String currentmood;
                                private String currenttrail;
                                
                                public String getUrl() {
									return url;
								}
								public void setUrl(String url) {
									this.url = url;
								}
								public String getPwd() {
									return pwd;
								}
								public void setPwd(String pwd) {
									this.pwd = pwd;
								}
								public String get_user() {
                                return user;
                                }
                                public void set_user(String user) {
                                 this.user =user;
                                }
                }