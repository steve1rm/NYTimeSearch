package me.androidbox.nytimessearch.model;

import com.google.gson.annotations.SerializedName;

import java.security.Key;
import java.util.List;

import static android.R.attr.name;

/**
 * Created by steve on 10/20/16.
 */

public class NYTimesSearch {
    private Response response;
    private String status;
    private String copyright;

    public String getCopyright() {
        return copyright;
    }

    public Response getResponse() {
        return response;
    }

    public String getStatus() {
        return status;
    }

    public static class Response {
        private Meta meta;
        private List<Docs> docs;

        public List<Docs> getDocs() {
            return docs;
        }

        public Meta getMeta() {
            return meta;
        }

        public static class Meta {
            private int hits;
            private int time;
            private int offset;

            public int getHits() {
                return hits;
            }

            public int getOffset() {
                return offset;
            }

            public int getTime() {
                return time;
            }
        }

        public static class Docs {
            private String web_url;
            private String snippet;
            private String lead_paragraph;
            @SerializedName("abstract")
            private String _abstract;
            private String print_page;
            private List<Blog> blog;
            private String source;
            private List<Multimedia> multimedia;
            private transient Headline headline;
            private List<Keywords> keywords;
            private String pub_date;
            private String document_type;
            private String news_desk;
            private String section_name;
            private String subsection_name;
            private transient Byline byline;
            private String type_of_material;
            private String word_count;
            private String slideshow_credits;

            public String get_abstract() {
                return _abstract;
            }

            public List<Blog> getBlog() {
                return blog;
            }

            public Byline getByline() {
                return byline;
            }

            public String getDocument_type() {
                return document_type;
            }

            public Headline getHeadline() {
                return headline;
            }

            public List<Keywords> getKeywords() {
                return keywords;
            }

            public String getLead_paragraph() {
                return lead_paragraph;
            }

            public List<Multimedia> getMultimedia() {
                return multimedia;
            }

            public String getNews_desk() {
                return news_desk;
            }

            public String getPrint_page() {
                return print_page;
            }

            public String getPub_date() {
                return pub_date;
            }

            public String getSection_name() {
                return section_name;
            }

            public String getSlideshow_credits() {
                return slideshow_credits;
            }

            public String getSnippet() {
                return snippet;
            }

            public String getSource() {
                return source;
            }

            public String getSubsection_name() {
                return subsection_name;
            }

            public String getType_of_material() {
                return type_of_material;
            }

            public String getWeb_url() {
                return web_url;
            }

            public String getWord_count() {
                return word_count;
            }

            public static class Blog {

            }

            public static class Multimedia {
                private int width;
                private String url;
                private int height;
                private String subtype;
                private String image;

                public int getHeight() {
                    return height;
                }

                public String getImage() {
                    return image;
                }

                public String getSubtype() {
                    return subtype;
                }

                public String getUrl() {
                    return url;
                }

                public int getWidth() {
                    return width;
                }

                public static class Legacy {
                    private String wide;
                    private String wideheight;
                    private String wideWidth;

                    public String getWide() {
                        return wide;
                    }

                    public String getWideheight() {
                        return wideheight;
                    }

                    public String getWideWidth() {
                        return wideWidth;
                    }
                }
            }

            public static class Headline {
                private String main;
                private String content_kicker;
                private String kicker;
                private String print_headline;

                public String getContent_kicker() {
                    return content_kicker;
                }

                public String getKicker() {
                    return kicker;
                }

                public String getMain() {
                    return main;
                }

                public String getPrint_headline() {
                    return print_headline;
                }
            }

            public static class Keywords {
                private String rank;
                private String is_major;
                private String name;
                private String value;

                public String getIs_major() {
                    return is_major;
                }

                public String getName() {
                    return name;
                }

                public String getRank() {
                    return rank;
                }

                public String getValue() {
                    return value;
                }
            }

            public static class Byline {
                private String original;
                private List<Person> person;

                public String getOriginal() {
                    return original;
                }

                public List<Person> getPerson() {
                    return person;
                }

                public static class Person {
                   private String firstname;
                    private String middlename;
                    private String lastname;
                    private int rank;
                    private String role;
                    private String organization;

                    public String getFirstname() {
                        return firstname;
                    }

                    public String getLastname() {
                        return lastname;
                    }

                    public String getMiddlename() {
                        return middlename;
                    }

                    public String getOrganization() {
                        return organization;
                    }

                    public int getRank() {
                        return rank;
                    }

                    public String getRole() {
                        return role;
                    }
                }
            }
        }
    }
}
