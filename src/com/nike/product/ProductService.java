package com.nike.product;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nike.action.ActionFoward;
import com.nike.file.FileDAO;
import com.nike.file.FileDTO;
import com.nike.page.MakePager;
import com.nike.page.Pager;
import com.nike.page.RowNumber;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ProductService {

	private ProductDAO productDAO;
	private FileDAO fileDAO;

	public ProductService() {
		productDAO = new ProductDAO();
		fileDAO = new FileDAO();
	}

	public ActionFoward selectList(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();

		int curPage = 1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		String kind = request.getParameter("kind");
		if (kind == null || kind.equals("")) {
			kind = "productCode";
		}
		String search = request.getParameter("search");
		String order = request.getParameter("order");
		if (order == null || order.equals("")) {
			order = "productcode desc";
		}
		String text = request.getParameter("text");
		if (text == null || text.equals("")) {
			text = "신상품순"; 
		}
		MakePager makePager = new MakePager(curPage, search, kind);
		RowNumber rowNumber = makePager.makeRow();

		try {

			List<ProductDTO> ar = productDAO.selectList(rowNumber, order);
			List<FileDTO> far = new ArrayList<>();
			for (int i = 0; i < ar.size(); i++) {
				FileDTO fileDTO = fileDAO.selectOne(ar.get(i).getProductCode());
				far.add(fileDTO);
			}

			int totalCount = productDAO.getCount(rowNumber.getSearch());
			Pager pager = makePager.makePage(totalCount);

			request.setAttribute("text", text);
			request.setAttribute("list", ar);
			request.setAttribute("file", far);
			request.setAttribute("pager", pager);
			request.setAttribute("board", "product");
			request.setAttribute("totalCount", totalCount);
			actionFoward.setPath("../WEB-INF/view/product/productList.jsp");

		} catch (Exception e) {

			request.setAttribute("message", "Fail");
			request.setAttribute("path", "../index.jsp");
			actionFoward.setPath("../WEB-INF/common/result.jsp");
			e.printStackTrace();
		}
		actionFoward.setCheck(true);

		return actionFoward;
	}

	public ActionFoward buy(HttpServletRequest request, HttpServletResponse response) {

		ActionFoward actionFoward = new ActionFoward();
		ProductDTO productDTO = new ProductDTO();
		
		actionFoward.setCheck(true);
		actionFoward.setPath("");
		return actionFoward;
		
	}
	public ActionFoward selectOne(HttpServletRequest request, HttpServletResponse response) {

		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		if (method.equals("POST")) {

			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductCode(request.getParameter("code"));
			productDTO.setProductName(request.getParameter("name"));
			productDTO.setPrice(Integer.parseInt(request.getParameter("price")));
			int quantity = Integer.parseInt((request.getParameter("quantity")));

			try {
				productDTO = productDAO.selectOne(productDTO.getProductCode());
				productDTO.setProductSize(request.getParameter("size"));
				// List<FileDTO> ar = new ArrayList<>();
				FileDTO fileDTO = fileDAO.selectOne(productDTO.getProductCode());

				request.setAttribute("file", fileDTO);
				request.setAttribute("item", productDTO);
				request.setAttribute("quantity", quantity);

				actionFoward.setCheck(true);
				actionFoward.setPath("../WEB-INF/view/product/checkout.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			ProductDTO productDTO = null;
			String code = request.getParameter("code");
			try {
				
				productDTO = productDAO.selectOne(code);
				int c = productDTO.getHit()+1;
				productDTO.setHit(c);
				int result = productDAO.updateHit(productDTO);
				
			
			
				String s = String.valueOf(productDTO.getProductSize());
				String[] ss = s.split("-");
				List<FileDTO> ar = new ArrayList<>();
				ar = fileDAO.selectList(code);
				result = productDAO.getScoreAvg(productDTO.getProductCode());
				int reviewTotal = productDAO.getReviewTotal(productDTO.getProductCode());
				request.setAttribute("size", ss);
				request.setAttribute("file", ar);
				request.setAttribute("pDTO", productDTO);
				request.setAttribute("board", "product");
				request.setAttribute("scoreAvg", result);
				request.setAttribute("reviewTotal", reviewTotal);

				actionFoward.setCheck(true);
				actionFoward.setPath("../WEB-INF/view/product/productSelectOne.jsp");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return actionFoward;
	}

	public ActionFoward insert(HttpServletRequest request, HttpServletResponse response) {

		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		if (method.equals("POST")) {
			String message = "fail";
			String path = "./productList.do";
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			// 파일의 크기
			int maxSize = 1024 * 1024 * 20;
			// 파일 저장공간
			String save = request.getServletContext().getRealPath("upload");
			System.out.println(save);
			File file = new File(save);
			if (!file.exists()) {
				file.mkdirs();
			} // 파일이 없으면 파일을 만들기
			try {
				System.out.println(save);
				MultipartRequest multi = new MultipartRequest(request, save, maxSize, "utf-8",
						new DefaultFileRenamePolicy());
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductCode(productDAO.getCode());
				productDTO.setProductName(multi.getParameter("name"));
				productDTO.setPrice(Integer.parseInt(multi.getParameter("price")));
				productDTO.setKind(multi.getParameter("kind"));
				productDTO.setManufacturerCode(multi.getParameter("mCode"));
				productDTO.setWriter(multi.getParameter("writer"));
				productDTO.setContents(multi.getParameter("contents"));
				String s = "-";
				String productSize = (multi.getParameter("sizemin") + s + multi.getParameter("sizemax"));
				productDTO.setProductSize(productSize);

				int result = productDAO.insert(productDTO);
				if (result > 0) {

					FileDAO fileDAO = new FileDAO();
					Enumeration<Object> e = multi.getFileNames(); // write 에 input file name을 가져옴 (e)
					int i = 0;
					while (e.hasMoreElements()) {
						String p = (String) e.nextElement();
						FileDTO fileDTO = new FileDTO();
						fileDTO.setPut("fname" + i);
						fileDTO.setProductCode(productDTO.getProductCode());
						fileDTO.setFname(multi.getFilesystemName(p));
						fileDTO.setOname(multi.getOriginalFileName(p));
						i++;

						if (fileDTO.getFname() != null) {
							result = fileDAO.insert(fileDTO);
						}
					}
					if (result > 0) {
						message = "Success";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("message", message);
			request.setAttribute("path", path);

		} else {
			request.setAttribute("board", "product");
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/product/productWrite.jsp");
		}

		return actionFoward;

	}

	public ActionFoward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();

		try {
			String code = request.getParameter("code");
			List<FileDTO> ar = fileDAO.selectList(code);
			fileDAO.delete(code);
			int result = productDAO.delete(code);
			if (result > 0) {
				String path = request.getServletContext().getRealPath("upload");
				File file = null;
				for (int i = 0; i < ar.size(); i++) {
					file = new File(path, ar.get(i).getFname());
					file.delete();
				}

				request.setAttribute("message", "Success");
				request.setAttribute("path", "./productList.do");

			} else {
				request.setAttribute("message", "Fail");
				request.setAttribute("path", "./productList.do");
			}
		} catch (Exception e) {
			request.setAttribute("message", "Fail");
			request.setAttribute("path", "./productList.do");
			e.printStackTrace();
		}
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/common/result.jsp");

		return actionFoward;
	}

	public ActionFoward update(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		if (method.equals("POST")) {
			int max = 1024 * 1024 * 10;
			String path = request.getServletContext().getRealPath("upload");
			File file = new File(path);

			if (!file.exists()) {
				file.mkdirs();
			}
			String message = "upload Fail";
			try {
				MultipartRequest multi = new MultipartRequest(request, path, max, "utf-8",
						new DefaultFileRenamePolicy());
				ProductDTO productDTO = new ProductDTO();
				productDTO = productDAO.selectOne(multi.getParameter("code"));
				productDTO.setProductName(multi.getParameter("name"));
				productDTO.setKind(multi.getParameter("kind"));
				productDTO.setPrice(Integer.parseInt(multi.getParameter("price")));
				productDTO.setContents(multi.getParameter("contents"));

				List<FileDTO> ar = fileDAO.selectList(multi.getParameter("code"));

				for (int i = 0; i < ar.size(); i++) {

					FileDTO fileDTO = null;
					fileDTO = new FileDTO();

					file = multi.getFile("f" + i);

					if (file != null) {
						file = new File(path, ar.get(i).getFname());
						file.delete();
						ar.get(i).setFname(multi.getFilesystemName("f" + i));
						ar.get(i).setOname(multi.getOriginalFileName("f" + i));

						ar.add(fileDTO);
					}

					fileDAO.update(ar.get(i));
				}
				int result = productDAO.update(productDTO);
				if (result > 0) {
					request.setAttribute("product", productDTO);
					request.setAttribute("file", ar);
					message = "Update Success";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("message", message);
			request.setAttribute("path", "./productList.do");

			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/common/result.jsp");

		} else {
			try {
				String code = request.getParameter("code");
				ProductDTO productDTO = productDAO.selectOne(code);
				FileDAO fileDAO = new FileDAO();
				FileDTO fileDTO = new FileDTO();
				fileDTO.setProductCode(code);
				fileDTO.setPut("M");
				List<FileDTO> ar = fileDAO.selectList(productDTO.getProductCode());
				request.setAttribute("pDTO", productDTO);
				request.setAttribute("ar", ar);
				request.setAttribute("board", "product");
				actionFoward.setCheck(true);
				actionFoward.setPath("../WEB-INF/view/product/productUpdate.jsp");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return actionFoward;
	}

}
