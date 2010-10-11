package name.fraser.neil.plaintext;

import static java.lang.Character.CONTROL;

import java.util.regex.Pattern;

public class StandardBreakScorer implements SemanticBreakScorer {
	  private final Pattern BLANKLINEEND
      = Pattern.compile("\\n\\r?\\n\\Z", Pattern.DOTALL);
  private final Pattern BLANKLINESTART
      = Pattern.compile("\\A\\r?\\n\\r?\\n", Pattern.DOTALL);


	public int scoreBreakOver(String one, String two) {
		if (one.length() == 0 || two.length() == 0) {
		      // Edges are the best.
		      return 5;
		    }

		    // Each port of this function behaves slightly differently due to
		    // subtle differences in each language's definition of things like
		    // 'whitespace'.  Since this function's purpose is largely cosmetic,
		    // the choice has been made to use each language's native features
		    // rather than force total conformity.
		    int score = 0;
		    // One point for non-alphanumeric.
		    char endOne = one.charAt(one.length() - 1);
			char startTwo = two.charAt(0);
			if (!Character.isLetterOrDigit(endOne) || !Character.isLetterOrDigit(startTwo)) {
		      score++;
		      // Two points for whitespace.
		      if (Character.isWhitespace(endOne) || Character.isWhitespace(startTwo)) {
		        score++;
		        // Three points for line breaks.
		        if (Character.getType(endOne) == CONTROL || Character.getType(startTwo) == CONTROL) {
		          score++;
		          // Four points for blank lines.
		          if (BLANKLINEEND.matcher(one).find() || BLANKLINESTART.matcher(two).find()) {
		            score++;
		          }
		        }
		      }
		    }
		    return score;
	}
}
