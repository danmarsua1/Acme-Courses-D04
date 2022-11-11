<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for learner particular
- purposes.  The copyright owner does not offer learner warranties or representations, nor do
- they accept learner liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="learner.help-request.form.label.ticker" path="ticker"/>
	<acme:input-textbox code="learner.help-request.form.label.statement" path="statement"/>
	<acme:input-textbox code="learner.help-request.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="learner.help-request.form.label.initDate" path="initDate"/>
	<acme:input-textbox code="learner.help-request.form.label.finishDate" path="finishDate"/>
	<acme:input-textbox code="learner.help-request.form.label.status" path="status"/>
	<acme:input-textbox code="learner.help-request.form.label.budget" path="budget"/>
	<acme:input-textbox code="learner.help-request.form.label.link" path="link"/>
</acme:form>
