package com.yukthi.automation.validations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yukthi.automation.AbstractValidation;
import com.yukthi.automation.AutomationContext;
import com.yukthi.automation.Executable;
import com.yukthi.automation.IApplicationConfiguration;
import com.yukthi.automation.IExecutionLogger;
import com.yukthi.utils.exceptions.InvalidStateException;

/**
 * SQL based validation.
 */
@Executable("validateWithSql")
public class SqlValidation extends AbstractValidation
{
	private static Logger logger = LogManager.getLogger(SqlValidation.class);

	/**
	 * Result row expectation.
	 * 
	 * @author akiran
	 */
	public static class ExpectedRow
	{
		/**
		 * Expected column to value mapping.
		 */
		private Map<String, String> columnToValue = new HashMap<>();

		/**
		 * Adds the column and value expectation.
		 * 
		 * @param name
		 *            Name of column.
		 * @param value
		 *            Expected value.
		 */
		public void addColumn(String name, String value)
		{
			if(name == null || name.trim().length() == 0)
			{
				throw new NullPointerException("Name can not be null or empty");
			}

			if(value == null || value.trim().length() == 0)
			{
				throw new NullPointerException("Value can not be null or empty");
			}

			columnToValue.put(name, value);
		}
	}

	/**
	 * Query to execute.
	 */
	private String query;

	/**
	 * Rows and expected values.
	 */
	private List<ExpectedRow> expectedRows = new ArrayList<>();

	/**
	 * Adds row expectation.
	 * 
	 * @param row
	 *            row expectation
	 */
	public void addExpectedRow(ExpectedRow row)
	{
		expectedRows.add(row);
	}

	/**
	 * Sets the query to execute.
	 *
	 * @param query
	 *            the new query to execute
	 */
	public void setQuery(String query)
	{
		this.query = query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.ui.automation.IValidation#execute(com.yukthi.ui.automation.
	 * AutomationContext, com.yukthi.ui.automation.IExecutionLogger)
	 */
	@Override
	public boolean validate(AutomationContext context, IExecutionLogger exeLogger)
	{
		IApplicationConfiguration appConfig = context.getAppConfiguration();

		if(!(appConfig instanceof ISqlConfiguration))
		{
			throw new InvalidStateException("Specified application configuration does not hold Sql configuration - {}", appConfig.getClass().getName());
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try
		{
			connection = ((ISqlConfiguration) appConfig).getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			int rowIdx = 0;
			ExpectedRow row = null;
			String actualVal = null, expectedVal = null;

			while(rs.next())
			{
				if(expectedRows.size() <= rowIdx)
				{
					exeLogger.error("Actual rows are more than expected row count- {}", expectedRows.size());
					return false;
				}

				row = expectedRows.get(rowIdx);

				for(String column : row.columnToValue.keySet())
				{
					actualVal = rs.getString(column);
					expectedVal = row.columnToValue.get(column);

					if(!expectedVal.equals(actualVal))
					{
						exeLogger.error("At row {} for column {} expected value '{}' is not matching with actual value - {}", rowIdx, column, expectedVal, actualVal);
						return false;
					}
				}
			}

			return true;
		} catch(SQLException ex)
		{
			throw new InvalidStateException(ex, "An erorr occurred while executing sql validation with query - {}", query);
		} finally
		{
			try
			{
				if(rs != null)
				{
					rs.close();
				}

				if(statement != null)
				{
					statement.close();
				}

				if(connection != null)
				{
					connection.close();
				}
			} catch(Exception ex)
			{
				logger.warn("An error occurred while closing sql resources", ex);
			}
		}
	}
}
