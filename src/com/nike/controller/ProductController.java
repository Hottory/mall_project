package com.nike.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nike.action.ActionFoward;
import com.nike.product.ProductService;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductController() {
		super();
		productService = new ProductService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String command = request.getPathInfo();
		ActionFoward actionFoward = null;
		System.out.println(1);
		if (command.equals("/productList.do")) {

			actionFoward = productService.selectList(request, response);
		}else if(command.equals("/productSelectOne.do")) {
			actionFoward = productService.selectOne(request, response);
		}else if(command.equals("/productWrite.do")) {
			actionFoward = productService;
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
