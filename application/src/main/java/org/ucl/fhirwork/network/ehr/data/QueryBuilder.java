package org.ucl.fhirwork.network.ehr.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryBuilder
{
    private List<String> selectStatements;
    private List<String> fromStatements;
    private List<String> containsStatements;

    public QueryBuilder()
    {
        selectStatements = new ArrayList<>();
        fromStatements = new ArrayList<>();
        containsStatements = new ArrayList<>();
    }

    public void appendSelectStatement(String text, String path)
    {
        appendSelectStatement(text, path, "");
    }

    public void appendSelectStatement(String text, String path, String label)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (selectStatements.isEmpty()) {
            stringBuilder.append("select ");
        }
        stringBuilder.append(text);
        stringBuilder.append("/");
        stringBuilder.append(path);

        if (! label.isEmpty()) {
            stringBuilder.append(" as ");
            stringBuilder.append(label);
        }
        selectStatements.add(stringBuilder.toString());
    }

    public void appendFromStatement(String type, String specialization)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from ");
        stringBuilder.append(type);
        stringBuilder.append("[");
        stringBuilder.append(specialization);
        stringBuilder.append("]");
        fromStatements.add(stringBuilder.toString());
    }

    public void appendContainsStatement(String type, String label)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("contains ");
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append(label);
        containsStatements.add(stringBuilder.toString());
    }

    public void appendContainsStatement(String type, String label, String specialization)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("contains ");
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append(label);
        stringBuilder.append("[");
        stringBuilder.append(specialization);
        stringBuilder.append("]");
        containsStatements.add(stringBuilder.toString());
    }

    @Override
    public String toString() {
        return build();
    }

    public String build()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(combineStatements(selectStatements, ", ", " "));
        stringBuilder.append(combineStatements(fromStatements, " ", " "));
        stringBuilder.append(combineStatements(containsStatements, " ", " "));
        return stringBuilder.toString();
    }

    private String combineStatements(List<String> statements, String delimiter, String suffix)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> statementIterator = statements.iterator();
        while (statementIterator.hasNext()) {

            String statement = statementIterator.next();
            stringBuilder.append(statement);

            if (statementIterator.hasNext()) {
                stringBuilder.append(delimiter);
            }
        }
        stringBuilder.append(suffix);
        return stringBuilder.toString();
    }
}
