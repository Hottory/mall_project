<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="../../../temp/bootStrap.jsp" />
<script type="text/javascript">
	$(function() {
		$(".btn-delete").click(function() {
			alert("1개 상품이 삭제되었습니다");
			location.reload();
		});
		
		$(".btn-cart-delete-All").click(function() {
			alert("전체 상품이 삭제되었습니다");
		});
	});
</script>
<link href="/mall_project/css/index.css" rel="stylesheet"
	type="text/css">
	</head>
<c:import url="../../../temp/header.jsp" />
<section class="wrapper">
	<section class="content-area">
		<section class="pt_cart">
			<article class="contents margin-xsmall">
				<h2 class="cart-title">장바구니</h2>
				<div class="mc-cart-num">
					<span>${blist.size()}개 상품</span>
				</div>
				<div class="item-container" data-module-cart="">
					<div class="item-list-wrap" id="cart">
						<div class="mypage-cart" id="current-item-wrap">
							<div class="product-select-all">
								<a class="btn-cart-delete-All" href="${pageContext.request.contextPath}/basket/basketDeleteall.do?id=${member.id}">전체삭제</a>
							</div>
							<c:forEach items="${blist}" var="bDTO" varStatus="i">
								<div class="product-opt_cart" id="productRow21099499">
									<input type="hidden" name="categoryId" value="10001"> <input
										type="hidden" name="productId" data-id="10000009435"
										value="10000009435"> <input type="hidden" name="skuId"
										value="10000045730"> <input type="hidden"
										name="orderItemId" value="21099499"> <input
										type="hidden" name="quantity" data-quantity="1" value="1">
									<input type="hidden" name="producturl"
										value="${bDTO.productName}">
									<div class="item-detail">
										<div class="item-info">
											<span class="img-wrap"> <img
												src="/mall_project/upload/${bDTO.fname}"
												alt="${bDTO.productName}">
											</span>
											<div class="info-wrap">
												<a class="tit"
													href="${pageContext.request.contextPath}/product/productSelectOne.do?code=${bDTO.productCode}"
													title="${bDTO.productName}">${bDTO.productName}</a>
												<div class="style-code" data-model="AJ7747-700">스타일 :
													${bDTO.productCode}</div>
												<span class="uk-hidden"></span>
												<!-- skuOptionDisplayProcess -->
												<div class="current-option-wrap">
													<input type="hidden" name="FW_SIZE"
														value="${bDTO.productSize}">
													<!-- bundle product -->
													<!-- product -->
													<span class="opt" data-opt="{FW_SIZE:270}"
														data-attribute-name="FW_SIZE">사이즈 :
														${bDTO.productSize}</span>
												</div>
												<!-- itemAttribute -->
												<span class="opt quantity">수량 : 1</span>
											</div>
										</div>
										<div class="option-wrap">
											<div>
												<button href="#"
													class="optchange-btn btn-link sky-blue width-max">옵션
													변경</button>
											</div>
										</div>
										<div class="total-price">
											<strong class="retail-price priceText"
												data-price="${bDTO.price}">${bDTO.price}원</strong>
										</div>
										<div class="delete-btn">
											<a class="btn-delete"
												href="${pageContext.request.contextPath}/basket/basketDelete.do?num=${bDTO.num}&id=${member.id}">
												<i class="icon-delete_bold"></i>
											</a>
										</div>
									</div>
									<div class="promo-detail"></div>
									<div class="btn-box">
										<div class="btn-wishlist-add"></div>
									</div>
								</div>
								<input type="hidden" ${total=total+bDTO.price} />
							</c:forEach>
						</div>
						<div class="pc-only">
							<div class="mypage-cart later"></div>
						</div>
					</div>
					<div class="product-checkout">
						<strong class="tit">주문예정금액</strong>
						<div
							class="product-checkout__in-box product-checkout__in-box--pay">
							<div class="info-price">
								<span class="item-price"> <span class="label" style="text-align: left;">상품
										금액</span> <span class="price"> <strong>${total} 원</strong>
								</span>
								</span> <span class="delivery-price"> <span class="label" style="text-align: left;">예상
										배송비</span> <span class="price"><strong>0 원</strong></span>
								</span> <span class="item-price"> <span class="label" style="text-align: left;">상품
										할인 금액</span> <span class="price sale"> <strong>0 원</strong>
								</span>
								</span> <span class="item-price"> <span class="label" style="text-align: left;">주문
										할인 금액</span> <span class="price sale"> <strong> 0 원 </strong>
								</span>
								</span>
							</div>
							<div class="total-price">
								<span class="label" style="text-align: left;">총 결제 예정 금액</span> <span
									class="price sale total priceText" data-price="${total}"><strong>${total}
										원</strong></span>
							</div>
							<a class="btn-link xlarge width-max btn-order indian-red"
								href="${pageContext.request.contextPath}/basket/basketList.do?id=${member.id}">주문하기
								<button id="btn-order"></button>
							</a>
						</div>
						<div class="info">
							<span><ul>
									<li>전 상품 무료배송 입니다.</li>
									<li>장기간 장바구니에 보관하신 상품은 시간이 지남에 따라 가격과 혜택이 변동 될 수 있으며, 최대
										30일동안 보관됩니다.</li>
								</ul></span>
						</div>
					</div>
					<div class="mobile-only">
						<div class="mypage-cart later"></div>
					</div>
				</div>
			</article>
		</section>
	</section>
</section>
<c:import url="../../../temp/footer.jsp" />
</html>