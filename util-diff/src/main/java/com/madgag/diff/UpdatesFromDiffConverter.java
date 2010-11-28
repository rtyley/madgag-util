package com.madgag.diff;

import name.fraser.neil.plaintext.diff_match_patch.Diff;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class UpdatesFromDiffConverter {
	public List<Update> convert(List<Diff> diffs) {
		UpdateSequence updateSequence=new UpdateSequence();
		for (Diff diff : diffs) {
            switch (diff.operation) {
                case EQUAL:
                    updateSequence.append(new Update(diff.text));
                    break;
                case INSERT:
                    updateSequence.append(new Update("", diff.text));
                    break;
                case DELETE:
                    updateSequence.append(new Update(diff.text,""));
                    break;
            }
		}
        return newArrayList(updateSequence.updates);
	}

    public class UpdateSequence {
        private final LinkedList<Update> updates=new LinkedList<Update>();

        public void append(Update update) {
            if (update.isEmpty()) {
                return;
            }
            if (updates.isEmpty()) {
                updates.add(update);
                return;
            }
            Update previousUpdate=updates.getLast();
            if (previousUpdate.isChange() == update.isChange()) {
                updates.removeLast();
                updates.addLast(previousUpdate.append(update));
            } else {
                updates.addLast(update);
            }
        }
    }
}
