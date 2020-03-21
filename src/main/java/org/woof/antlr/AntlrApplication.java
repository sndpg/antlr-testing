package org.woof.antlr;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

@Slf4j
public class AntlrApplication {

    public static void main(String[] args) {
        String testSql = """
                select * from test.first
                where name = 'abc';
                """;

        CharStream input = new CaseChangingCharStream(CharStreams.fromString(testSql),
                CaseChangingCharStream.CharManipulationMode.TO_UPPER_CASE);
        org.woof.antlr.PlSqlLexer lexer = new org.woof.antlr.PlSqlLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // consider enabling intellisense support for files greater tha 2.something MB in IntelliJ IDEA
        // Help -> Edit Custom Properties -> idea.max.intellisense.filesize=8192
        org.woof.antlr.PlSqlParser parser = new org.woof.antlr.PlSqlParser(tokens);

        ParseTree parseTree = parser.select_statement().getPayload();
        log.info("parse tree: " + parseTree.toStringTree(parser));

    }

}

