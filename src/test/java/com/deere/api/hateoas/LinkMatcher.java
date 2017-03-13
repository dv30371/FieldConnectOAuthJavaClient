package com.deere.api.hateoas;

import com.deere.api.axiom.generated.v3.Link;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.IsEqual;

public class LinkMatcher extends BaseMatcher<Link> {
    private final Link linkType;

    public LinkMatcher(final Link linkType) {
        this.linkType = linkType;
    }

    public static LinkMatcher aLinkThatMatches(final Link link) {
        return new LinkMatcher(link);
    }

    @Override public boolean matches(final Object item) {
        return item instanceof Link
               && new IsEqual<String>(linkType.getRel()).matches(((Link) item).getRel())
               && new IsEqual<String>(linkType.getUri()).matches(((Link) item).getUri())
               && new IsEqual<Boolean>(linkType.isFollowable()).matches(((Link) item).isFollowable());
    }

    @Override public void describeTo(final Description description) {
        final Boolean followable = linkType.isFollowable();
        final String followableDesc;
        if (null == followable || !followable) {
            followableDesc = "an unfollowable ";
        } else {
            followableDesc = "a followable ";
        }
        description.appendText(followableDesc + "link to ")
                   .appendValue(linkType.getUri())
                   .appendText(" with rel ")
                   .appendValue(linkType.getRel());
    }
}
