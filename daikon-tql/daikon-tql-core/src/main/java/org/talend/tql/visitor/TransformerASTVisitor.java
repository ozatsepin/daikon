package org.talend.tql.visitor;

import java.lang.reflect.Array;
import java.util.stream.Stream;

import org.talend.tql.model.AllFields;
import org.talend.tql.model.AndExpression;
import org.talend.tql.model.ComparisonExpression;
import org.talend.tql.model.ComparisonOperator;
import org.talend.tql.model.Expression;
import org.talend.tql.model.FieldBetweenExpression;
import org.talend.tql.model.FieldCompliesPattern;
import org.talend.tql.model.FieldContainsExpression;
import org.talend.tql.model.FieldInExpression;
import org.talend.tql.model.FieldIsEmptyExpression;
import org.talend.tql.model.FieldIsInvalidExpression;
import org.talend.tql.model.FieldIsValidExpression;
import org.talend.tql.model.FieldMatchesRegex;
import org.talend.tql.model.FieldReference;
import org.talend.tql.model.FieldWordCompliesPattern;
import org.talend.tql.model.LiteralValue;
import org.talend.tql.model.NotExpression;
import org.talend.tql.model.OrExpression;
import org.talend.tql.model.TqlElement;

public abstract class TransformerASTVisitor implements IASTVisitor<Object> {

    @Override
    public Object visit(TqlElement elt) {
        return elt;
    }

    @Override
    public Object visit(ComparisonOperator elt) {
        return elt;
    }

    @Override
    public Object visit(LiteralValue elt) {
        return elt;
    }

    @Override
    public Object visit(FieldReference elt) {
        return new FieldReference(elt.getPath());
    }

    @Override
    public Object visit(Expression elt) {
        return elt;
    }

    @Override
    public Object visit(AndExpression elt) {
        return new AndExpression(transform(Expression.class, elt.getExpressions()));
    }

    @Override
    public Object visit(OrExpression elt) {
        return new OrExpression(transform(Expression.class, elt.getExpressions()));
    }

    @Override
    public Object visit(ComparisonExpression elt) {
        return new ComparisonExpression(elt.getOperator(),
                transform(TqlElement.class, elt.getField())[0], transform(TqlElement.class, elt.getValueOrField())[0]);
    }

    private <T extends TqlElement> T[] transform(Class<T> clazz, T... input) {
        return (T[]) Stream.of(input).map(e -> e.accept(this)).toArray(i -> (T[]) Array.newInstance(clazz, i));
    }

    @Override
    public Object visit(FieldInExpression elt) {
        return new FieldInExpression(transform(TqlElement.class, elt.getField())[0],
                transform(LiteralValue.class, elt.getValues()));
    }

    @Override
    public Object visit(FieldIsEmptyExpression elt) {
        return new FieldIsEmptyExpression(transform(TqlElement.class, elt.getField())[0]);
    }

    @Override
    public Object visit(FieldIsValidExpression elt) {
        return new FieldIsValidExpression(transform(TqlElement.class, elt.getField())[0]);
    }

    @Override
    public Object visit(FieldIsInvalidExpression elt) {
        return new FieldIsInvalidExpression(transform(TqlElement.class, elt.getField())[0]);
    }

    @Override
    public Object visit(FieldMatchesRegex elt) {
        return new FieldMatchesRegex(transform(TqlElement.class, elt.getField())[0], elt.getRegex());
    }

    @Override
    public Object visit(FieldCompliesPattern elt) {
        return new FieldCompliesPattern(transform(TqlElement.class, elt.getField())[0], elt.getPattern());
    }

    @Override
    public Object visit(FieldWordCompliesPattern elt) {
        return new FieldWordCompliesPattern(transform(TqlElement.class, elt.getField())[0], elt.getPattern());
    }

    @Override
    public Object visit(FieldBetweenExpression elt) {
        return new FieldBetweenExpression(transform(TqlElement.class, elt.getField())[0],
                transform(LiteralValue.class, elt.getLeft())[0], transform(LiteralValue.class, elt.getRight())[0],
                elt.isLowerOpen(), elt.isUpperOpen());
    }

    @Override
    public Object visit(NotExpression elt) {
        return new NotExpression(transform(Expression.class, elt.getExpression())[0]);
    }

    @Override
    public Object visit(FieldContainsExpression elt) {
        return new FieldContainsExpression(transform(TqlElement.class, elt.getField())[0], elt.getValue(),
                elt.isCaseSensitive());
    }

    @Override
    public Object visit(AllFields allFields) {
        return new AllFields();
    }
}
