package com.nike.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nike.action.ActionFoward;
import com.nike.member.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemberController() {
		super();
		// TODO Auto-generated constructor stub
		memberService = new MemberService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getPathInfo();
		HttpSession session = request.getSession();
		ActionFoward actionFoward = null;

		if (command.equals("/memberList.do")) {
			actionFoward = memberService.selectList(request, response);
		} else if (command.equals("/memberJoin.do")) {
			actionFoward = memberService.join(request, response);
		} else if (command.equals("/memberCheckId.do")) {
			actionFoward = memberService.checkId(request, response);
		} else if (command.equals("/memberCheckSns.do")) {
			actionFoward = memberService.checkSns(request, response);
		} else if (command.equals("/memberLogin.do")) {
			if (session.getAttribute("member") == null) {
				actionFoward = memberService.login(request, response);
			} else {
				request.setAttribute("result", "이미 로그인되어 있습니다");
				actionFoward = new ActionFoward();
				actionFoward.setCheck(true);
				actionFoward.setPath("../WEB-INF/view/member/memberCheckResult.jsp");
			}
		} else if (command.equals("/memberLogout.do")) {
			actionFoward = memberService.logout(request, response);
		} else if (command.equals("/memberSelectOne.do")) {
			actionFoward = memberService.selectOne(request, response);
		} else if (command.equals("/memberUpdate.do")) {
			actionFoward = memberService.update(request, response);
		} else if (command.equals("/memberDelete.do")) {
			actionFoward = memberService.delete(request, response);
		} else if (command.equals("/memberSnsLogin.do")) {
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/member/memberSnsLogin.jsp");
		} 
		else if (command.equals("/kakaoLogin.do")) {
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/member/kakaoLogin.jsp");
		} else if (command.equals("/facebookLogin.do")) {
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/member/facebookLogin.jsp");
		} else if (command.equals("/snsLogin.do")) {
			actionFoward = new ActionFoward();
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/member/snsLogin.jsp");
		}
		if (actionFoward.isCheck()) {
			RequestDispatcher view = request.getRequestDispatcher(actionFoward.getPath());
			view.forward(request, response);
		} else {
			response.sendRedirect(actionFoward.getPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
