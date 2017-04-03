/*
 *
 *   *
 *   *  *
 *   *  *   ~
 *   *  *   ~ The MIT License (MIT)
 *   *  *   ~
 *   *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *   *  *   ~
 *   *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *   *  *   ~ in the Software without restriction, including without limitation the rights
 *   *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *   *  *   ~ furnished to do so, subject to the following conditions:
 *   *  *   ~
 *   *  *   ~ The above copyright notice and this permission notice shall be included in all
 *   *  *   ~ copies or substantial portions of the Software.
 *   *  *   ~
 *   *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   *  *   ~ SOFTWARE.
 *   *  *   ~
 *   *  *   ~
 *   *  *
 *   *
 *   *
 *
 * /
 */

package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.*;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by qamatic on 2/20/16.
 */
public class StoredProcedure implements Executable<int[]> {

    private static final MintLeafLogger logger = MintLeafLogger.getLogger(StoredProcedure.class);
    private ConnectionContext connectionContext;
    private CallType callType;
    private ExecutionResultListener.Callable executionResultListener;
    private String procedureCall;

    private ParameterBinding.Callable parameterBinding;

    public StoredProcedure(ConnectionContext connectionContext, String procedureCall, CallType callType, ParameterBinding.Callable parameterBinding) {
        this.connectionContext = connectionContext;
        this.callType = callType;
        this.procedureCall = procedureCall;
        this.parameterBinding = parameterBinding;
        if (this.procedureCall == null)
            this.procedureCall = "";//avoid check everywhere crap
        if (this.callType != CallType.CUSTOMCALL) {
            this.procedureCall = procedureCall.toUpperCase().trim();
        }
    }


    public String getSql() {
        if ((this.callType != CallType.CUSTOMCALL) && (!this.procedureCall.startsWith("CALL "))) {
            this.procedureCall = String.format("{ %sCALL %s }", this.callType == CallType.FUNCTION ? "? = " : "", this.procedureCall);
        }
        return this.procedureCall;
    }

    @Override
    public int[] execute() throws MintLeafException {
        logger.info(getSql());
        try (CallableStatement preparedStatement = connectionContext.getConnection().prepareCall(this.getSql())) {
            CallableBindingParameterSets parameterSets = new CallableBindingParameterSets(preparedStatement);
            if (parameterBinding != null) {
                parameterBinding.bindParameters(parameterSets);
            }

            int[] result = new int[]{preparedStatement.execute() ? 1 : 0};
            if (this.executionResultListener != null) {
                this.executionResultListener.onAfterExecuteSql(parameterSets);
            }
            return result;

        } catch (MintLeafException e) {
            logger.error("error fetching data", e);
            throw new MintLeafException(e);
        } catch (SQLException e) {
            logger.error(e);
            throw new MintLeafException(e);
        }
    }


    public void setExecutionResultListener(ExecutionResultListener.Callable statementResultListener) {
        this.executionResultListener = statementResultListener;
    }

    public enum CallType {
        PROC(-1), FUNCTION(Types.INTEGER), CUSTOMCALL(-1);

        private int dataType;

        private CallType(int dataType){
            this.dataType = dataType;
        }

        public int getReturnType(){
            return this.dataType;
        }

        public CallType returnsType(int type){
            dataType = type;
            return this;
        }
    }
}
