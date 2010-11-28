package com.madgag.diff;

import name.fraser.neil.plaintext.diff_match_patch.Diff;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static name.fraser.neil.plaintext.diff_match_patch.Operation.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class UpdatesFromDiffConverterTest {

    UpdatesFromDiffConverter converter=new UpdatesFromDiffConverter();

    @Test
    public void shouldMergeInsertThenDeleteIntoASingleUpdate() {
        List<Update> updates = converter.convert(asList(new Diff(INSERT, "TV"), new Diff(DELETE, "Radio")));
        assertThat(updates, is(asList(new Update("Radio","TV"))));
    }

    @Test
    public void shouldMergeDeleteThenInsertIntoASingleUpdate() {
        List<Update> updates = converter.convert(asList(new Diff(DELETE, "Radio"), new Diff(INSERT, "TV")));
        assertThat(updates, is(asList(new Update("Radio","TV"))));
    }

    @Test
    public void shouldMergeTwoEqualSectionsIntoOneUpdate() {
        List<Update> updates = converter.convert(asList(new Diff(EQUAL, "People"), new Diff(EQUAL, "Person")));
        assertThat(updates, is(asList(new Update("PeoplePerson","PeoplePerson"))));
    }

    @Test
    public void shouldConvertInsertBetweenTwoEquals() {
        List<Update> updates = converter.convert(asList(new Diff(EQUAL, "People"),new Diff(INSERT, "-"), new Diff(EQUAL, "Person")));
        assertThat(updates, is(asList(new Update("People"),new Update("","-"),new Update("Person"))));
    }

    @Test
    public void shouldConvertDeleteBetweenTwoEquals() {
        List<Update> updates = converter.convert(asList(new Diff(EQUAL, "People"),new Diff(DELETE, " make "), new Diff(EQUAL, "Person")));
        assertThat(updates, is(asList(new Update("People"),new Update(" make ",""),new Update("Person"))));
    }
}
