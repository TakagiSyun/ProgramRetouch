package ec;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {


			//
			int buyId = (int)session.getAttribute("id");
			Date buydate = (Date)session.getAttribute("buydate");
			System.out.println(buydate);
			String deliveryMethodName =(String) session.getAttribute("deliveryMethodName");
			int totalPrice = (int)session.getAttribute("totalPrice");
			BuyDataBeans BDB = new BuyDataBeans();
			BDB.setBuyDate(buydate);
			BDB.setDeliveryMethodName(deliveryMethodName);
			BDB.setTotalPrice(totalPrice);
			request.setAttribute("BDB", BDB);

			ArrayList<ItemDataBeans> ItemList = BuyDetailDAO.getItemDataBeansListByBuyId(buyId);
			 request.setAttribute("ItemList", ItemList);

			int userId = (int) session.getAttribute("userId");
			ArrayList<BuyDataBeans> userBuyList = BuyDetailDAO.getBuyDataBeansListByUserId(userId);
			request.setAttribute("userBuyList",userBuyList);



			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}
}
