<%@page import="com.sunilos.p4.util.MessageSource"%>
<%@page import="com.sunilos.p4.bean.PaymentBean"%>
<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>



<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

List list = ServletUtility.getList(request);
Iterator<PaymentBean> it = list.iterator();

String _err = ServletUtility.getErrorMessage(request);
MessageSource ms = MessageSource.getInstance();
%>

<div class="container-fluid px-4 py-4" style="max-width: 1100px;">

    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">

        <!-- Header -->
        <div class="card-header text-white border-0 py-3 px-4 d-flex justify-content-between align-items-center"
            style="background: linear-gradient(135deg, #0d2137 0%, #1565c0 100%);">

            <h5 class="mb-0 fw-bold">
                <i class="bi bi-credit-card-fill me-2"></i> <%= ms.get("payment.list") %>
        
            </h5>

            <a href="<%=ORSView.PAYMENT_CTL%>"
                class="btn btn-light text-primary fw-semibold">
                <i class="bi bi-plus-circle me-1"></i>
              <%= ms.get("payment.add") %>
            </a>

        </div>

        <form action="<%=ORSView.PAYMENT_LIST_CTL%>" method="POST">

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">

            <!-- Search -->
            <div class="p-3 bg-light border-bottom d-flex gap-2 align-items-center">

                <input type="text"
                    name="upi"
                    class="form-control"
                    style="max-width:250px;"
                    placeholder="Search by UPI">

                <button type="submit"
                    name="operation"
                    value="<%=BaseCtl.OP_SEARCH%>"
                    class="btn btn-primary">
                    <i class="bi bi-search"></i>
                    
                   <%= ms.get("button.search") %>
                </button>

                <button type="submit"
                    name="operation"
                    value="<%=BaseCtl.OP_DELETE%>"
                    class="btn btn-danger ms-auto"
                    onclick="return confirm('Delete selected records?')">

                    <i class="bi bi-trash"></i>
                    <%= ms.get("button.delete") %>

                </button>

            </div>

            <% if (_err != null && !_err.equals("")) { %>

            <div class="alert alert-danger m-3">
                <%= _err %>
            </div>

            <% } %>

            <!-- Table -->
            <div class="table-responsive">

                <table class="table table-hover align-middle mb-0">

                    <thead class="table-light">
                        <tr>
                            <th width="40">
                                <input type="checkbox"
                                    onclick="document.querySelectorAll('input[name=ids]').forEach(c=>c.checked=this.checked)">
                            </th>

                            <th> <%= ms.get("label.sno") %></th>
                            <th><%= ms.get("payment.upi") %></th>
                            <th><%= ms.get("payment.card") %></th>
                            <th><%= ms.get("payment.netbanking") %></th>
                            <th><%= ms.get("payment.wallet") %></th>
                            <th><%= ms.get("payment.paymentstatus") %></th>
                            <th><%= ms.get("label.action") %></th>
                        </tr>
                    </thead>

                    <tbody>

                    <%
                    while (it.hasNext()) {

                        PaymentBean bean = it.next();
                    %>

                        <tr>

                            <td>
                                <input type="checkbox"
                                    name="ids"
                                    value="<%=bean.getId()%>">
                            </td>

                            <td>
                                <%=index++%>
                            </td>

                            <td>
                                <%=bean.getUpi()%>
                            </td>

                            <td>
                                <%=bean.getCard()%>
                            </td>

                            <td>
                                <%=bean.getNetBanking()%>
                            </td>

                            <td>
                                <%=bean.getWallet()%>
                            </td>

                            <td>
                                <%=bean.getPaymentStatus()%>
                            </td>

                            <td>

                                <a href="<%=ORSView.PAYMENT_CTL%>?id=<%=bean.getId()%>"
                                    class="btn btn-sm btn-outline-primary">

                                    <i class="bi bi-pencil"></i>
                                    <%=ms.get("label.edit") %>

                                </a>

                            </td>

                        </tr>

                    <%
                    }
                    %>

                    </tbody>

                </table>

            </div>

            <!-- Footer -->
            <div class="p-3 border-top">
                <%@ include file="ListFooter.jsp"%>
            </div>

        </form>

    </div>

</div>