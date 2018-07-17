// Generated from Recon.g4 by ANTLR 4.7.1


package playground.swim.recon.antlr;


import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReconLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, WS=9, 
		BOOLEAN=10, NUMBER=11, STRING=12, IDENTIFIER=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "WS", 
		"BOOLEAN", "NUMBER", "STRING", "IDENTIFIER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "','", "';'", "':'", "'@'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, "WS", "BOOLEAN", 
		"NUMBER", "STRING", "IDENTIFIER"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ReconLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Recon.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\\\b\1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3"+
		"\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\6\n/\n\n\r\n\16\n\60\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13>\n\13\3\f\5\fA\n\f\3\f\3\f\7"+
		"\fE\n\f\f\f\16\fH\13\f\3\f\5\fK\n\f\3\r\3\r\7\rO\n\r\f\r\16\rR\13\r\3"+
		"\r\3\r\3\16\3\16\7\16X\n\16\f\16\16\16[\13\16\2\2\17\3\3\5\4\7\5\t\6\13"+
		"\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\3\2\7\5\2\13\f\17\17\"\""+
		"\3\2\63;\3\2\62;\6\2\62;C\\aac|\4\2C\\c|\2b\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\3\35\3\2\2\2\5\37\3\2\2\2\7!\3\2\2\2\t#\3\2\2\2\13%\3\2\2\2\r\'\3\2\2"+
		"\2\17)\3\2\2\2\21+\3\2\2\2\23.\3\2\2\2\25=\3\2\2\2\27@\3\2\2\2\31L\3\2"+
		"\2\2\33U\3\2\2\2\35\36\7}\2\2\36\4\3\2\2\2\37 \7\177\2\2 \6\3\2\2\2!\""+
		"\7.\2\2\"\b\3\2\2\2#$\7=\2\2$\n\3\2\2\2%&\7<\2\2&\f\3\2\2\2\'(\7B\2\2"+
		"(\16\3\2\2\2)*\7*\2\2*\20\3\2\2\2+,\7+\2\2,\22\3\2\2\2-/\t\2\2\2.-\3\2"+
		"\2\2/\60\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2\2\62\63\b\n\2\2"+
		"\63\24\3\2\2\2\64\65\7v\2\2\65\66\7t\2\2\66\67\7w\2\2\67>\7g\2\289\7h"+
		"\2\29:\7c\2\2:;\7n\2\2;<\7u\2\2<>\7g\2\2=\64\3\2\2\2=8\3\2\2\2>\26\3\2"+
		"\2\2?A\7/\2\2@?\3\2\2\2@A\3\2\2\2AJ\3\2\2\2BF\t\3\2\2CE\t\4\2\2DC\3\2"+
		"\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2GK\3\2\2\2HF\3\2\2\2IK\t\4\2\2JB\3\2"+
		"\2\2JI\3\2\2\2K\30\3\2\2\2LP\7$\2\2MO\t\5\2\2NM\3\2\2\2OR\3\2\2\2PN\3"+
		"\2\2\2PQ\3\2\2\2QS\3\2\2\2RP\3\2\2\2ST\7$\2\2T\32\3\2\2\2UY\t\6\2\2VX"+
		"\t\5\2\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z\34\3\2\2\2[Y\3\2\2\2"+
		"\n\2\60=@FJPY\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}