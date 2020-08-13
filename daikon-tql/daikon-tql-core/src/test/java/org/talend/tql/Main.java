package org.talend.tql;

import org.talend.tql.model.Expression;
import org.talend.tql.model.FieldReference;
import org.talend.tql.model.LiteralValue;
import org.talend.tql.parser.Tql;
import org.talend.tql.visitor.TransformerASTVisitor;

public class Main {

    public static void main(String[] args) {
        Expression expression = (Expression) Tql
                .parse("author='UUID_THAT_IS_NOT_VERY_USER_FRIENDLY' and age > 10") //
                .accept(new TransformerASTVisitor() {

                    private boolean isAuthor = false;

                    @Override
                    public Object visit(LiteralValue elt) {
                        if (isAuthor) {
                            isAuthor = false;
                            return new LiteralValue(LiteralValue.Enum.QUOTED_VALUE, "Nice Author");
                        } else {
                            return super.visit(elt);
                        }
                    }

                    @Override
                    public Object visit(FieldReference elt) {
                        if ("author".equals(elt.getPath())) {
                            isAuthor = true;
                        }
                        return super.visit(elt);
                    }
                });

        System.out.println(expression);
    }
}
