package model.dao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import model.entity.Board;

/**
 * boardテーブルと繋ぐDAOクラス
 */
public class BoardDAO {
	/**
	 * 唯一のインスタンスを生成する
	 */
	private static BoardDAO instance = new BoardDAO();

	/**
	 * 特定のデータベースとの接続(セッション)
	 */
	private Connection con;
	/**
	 * 静的SQL文を実行し、作成された結果を返すために使用されるオブジェクト
	 */
	private Statement st;
	/**
	 * シングルトンのため新規のインスタンスをつくらせない
	 */
	private BoardDAO() {
	}

	/**
	 * @return BoardDAOの唯一のインスタンス。
	 * 唯一のインスタンスを取得する。
	 */
	public static BoardDAO getInstance() {
		return instance;
	}

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 特定のデータベースとの接続(セッション)を生成する。
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void dbConnect(Logger logger) throws SQLException, SecurityException, IOException {
		ConnectionManager cm = ConnectionManager.getInstance();
		con = cm.connect(logger);
	}

	/**
	 * @throws SQLException データベース処理に問題があった場合。
	 * 静的SQL文を実行し、作成された結果を返すために使用されるオブジェクトを生成する。
	 */
	public void createSt() throws SQLException {
		st = con.createStatement();
	}

	/**
	 * 特定のデータベースとの接続(セッション)を切断する。
	 */
	public void dbDiscon() {
		try {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param board - 掲示板のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 最新の掲示板のレコードを取得する。
	 */
	public boolean searchBoard(Board board) throws SQLException, NoSuchAlgorithmException {

		boolean searchBoardChkFlag = false;

		// 最新の掲示板のレコードを取得する
		String sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 1;";
		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのBoardに取得したデータをセットしていく
		 */
		int index=0;
		if (rs.next()) {
			searchBoardChkFlag = true;
			index++;
			board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
			board.setSubject(index, rs.getString("Subject"));
			board.setContent(index, rs.getString("Content"));
			board.setUpdDate(1, rs.getString("Upd_Dt"));
		}

		return searchBoardChkFlag;
	}

	/**
	 * @param type - 初期表示または次のページ表示または前のページ表示
	 * @param boardId - 現在表示している掲示板のレコードID
	 * @param board - 掲示板のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 最新の掲示板のレコードを取得する。
	 */
	public boolean searchBoard(int type, int boardId, Board board) throws SQLException, NoSuchAlgorithmException {

		boolean searchBoardChkFlag = false;

		String sql = "";
		ResultSet rs;

		if(type==0){
			if(boardId==0) {
				sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 3;";
				rs = st.executeQuery(sql);
				int index=0;
				while (rs.next()) {
					searchBoardChkFlag = true;
					index++;
					board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
					board.setSubject(index, rs.getString("Subject"));
					board.setContent(index, rs.getString("Content"));
					board.setUpdDate(index, rs.getString("Upd_Dt"));
				}
			}else {
				sql = "select * from Board WHERE Board_Id='"+boardId+"';";
				rs = st.executeQuery(sql);
				int index=0;
				while (rs.next()) {
					searchBoardChkFlag = true;
					index++;
					board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
					board.setSubject(index, rs.getString("Subject"));
					board.setContent(index, rs.getString("Content"));
					board.setUpdDate(index, rs.getString("Upd_Dt"));
				}
				sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 3;";
				rs = st.executeQuery(sql);
				while (rs.next()) {
					searchBoardChkFlag = true;
					if(board.getBoardId(1)!=Integer.parseInt(rs.getString("Board_Id"))) {
						index++;
						board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
						board.setSubject(index, rs.getString("Subject"));
						board.setContent(index, rs.getString("Content"));
						board.setUpdDate(index, rs.getString("Upd_Dt"));
					}
				}
			}
		}else if(type==1){
			sql = "select * from Board WHERE Upd_Dt<'"+board.getUpdDate(1)+"' ORDER BY Upd_Dt DESC LIMIT 1;";
			rs = st.executeQuery(sql);
			int index=0;
			while (rs.next()) {
				searchBoardChkFlag = true;
				index++;
				board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
				board.setSubject(index, rs.getString("Subject"));
				board.setContent(index, rs.getString("Content"));
				board.setUpdDate(index, rs.getString("Upd_Dt"));
			}
			sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 3;";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				searchBoardChkFlag = true;
				if(board.getBoardId(1)!=Integer.parseInt(rs.getString("Board_Id"))) {
					index++;
					board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
					board.setSubject(index, rs.getString("Subject"));
					board.setContent(index, rs.getString("Content"));
					board.setUpdDate(index, rs.getString("Upd_Dt"));
				}
			}
		}else if(type==2){
			sql = "select * from Board WHERE Upd_Dt>'"+board.getUpdDate(1)+"' ORDER BY Upd_Dt ASC LIMIT 1;";
			rs = st.executeQuery(sql);
			int index=0;
			while (rs.next()) {
				searchBoardChkFlag = true;
				index++;
				board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
				board.setSubject(index, rs.getString("Subject"));
				board.setContent(index, rs.getString("Content"));
				board.setUpdDate(index, rs.getString("Upd_Dt"));
			}
			sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 3;";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				searchBoardChkFlag = true;
				if(board.getBoardId(1)!=Integer.parseInt(rs.getString("Board_Id"))) {
					index++;
					board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
					board.setSubject(index, rs.getString("Subject"));
					board.setContent(index, rs.getString("Content"));
					board.setUpdDate(index, rs.getString("Upd_Dt"));
				}
			}
		}else if(type==4) {
			sql = "select * from Board WHERE Board_Id='"+boardId+"';";
			rs = st.executeQuery(sql);
			int index=0;
			while (rs.next()) {
				searchBoardChkFlag = true;
				index++;
				board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
				board.setSubject(index, rs.getString("Subject"));
				board.setContent(index, rs.getString("Content"));
				board.setUpdDate(index, rs.getString("Upd_Dt"));
			}
			sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 3;";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				searchBoardChkFlag = true;
				if(board.getBoardId(1)!=Integer.parseInt(rs.getString("Board_Id"))) {
					index++;
					board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
					board.setSubject(index, rs.getString("Subject"));
					board.setContent(index, rs.getString("Content"));
					board.setUpdDate(index, rs.getString("Upd_Dt"));
				}
			}
		}

		return searchBoardChkFlag;
	}

	/**
	 * @param board - 掲示板のモデルクラスのインスタンス
	 * @return データベースにユーザIDに該当する掲示板のレコードがあればtrue、ない場合はfalse。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 全ての掲示板のレコードを取得する。
	 */
	public boolean searchAllBoard(Board board, String updDate, int type) throws SQLException, NoSuchAlgorithmException {

		boolean searchAllBoardChkFlag = false;

		String sql = "";
		// 8件の掲示板のレコードを取得する
		if(updDate==null) {
			sql = "select * from Board ORDER BY Upd_Dt DESC LIMIT 8;";
		}else {
			if(type==1) {
				sql = "select * from Board WHERE Upd_Dt>'"+updDate+"' ORDER BY Upd_Dt ASC LIMIT 8;";
			}else {
				sql = "select * from Board WHERE Upd_Dt<'"+updDate+"' ORDER BY Upd_Dt DESC LIMIT 8;";
			}
		}

		ResultSet rs = st.executeQuery(sql);

		/**
		 * entityのBoardに取得したデータをセットしていく
		 */
		int index=0;
		while (rs.next()) {
			searchAllBoardChkFlag = true;
			index++;
			board.setBoardId(index, Integer.parseInt(rs.getString("Board_Id")));
			board.setSubject(index, rs.getString("Subject"));
			board.setContent(index, rs.getString("Content"));
			board.setUpdDate(index, rs.getString("Upd_Dt"));
		}
		board.setNumbersRecords(index);

		return searchAllBoardChkFlag;
	}

	/**
	 * @param boardId - 掲示板のレコードID
	 * @param month - 月
	 * @param year - 年
	 * @param board - 掲示板のモデルクラスのインスタンス
	 * @return 掲示板のレコードの更新に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 指定されたboardIdとyearとmonthにマッチする掲示板のレコードを更新する。
	 */
	public boolean updateBoard(int updateId, int boardId , Board board) throws SQLException, NoSuchAlgorithmException {

		boolean updateBoardChkFlag = true;

		// boardIdがマッチした掲示板のレコードを更新する
		String sql = "";
		int num=0;
		sql = "update Board set Subject = \""+board.getSubject(1)+"\",Content = \""+board.getContent(1)+"\",Upd_Us = \""+updateId+"\" where Board_Id='"+ boardId + "';";
		num = st.executeUpdate(sql);
		if(num==0) {
			updateBoardChkFlag = false;
		}

		return updateBoardChkFlag;
	}

	/**
	 * @param board - 掲示板のモデルクラスのインスタンス
	 * @return 掲示板ののレコードの作成に成功した場合true、失敗した場合false。
	 * @throws SQLException。データベース処理に問題があった場合。
	 * @throws NoSuchAlgorithmException。ある暗号アルゴリズムが要求されたにもかかわらず、現在の環境では使用可能でない場合。
	 * 掲示板のレコードを新規作成する。
	 */
	public boolean insertBoard(int staffId, Board board) throws SQLException, NoSuchAlgorithmException {

		boolean insertBoardChkFlag = false;

		// 掲示板のレコードを作成する
		String sql = "";
		sql = "insert into Board value(DEFAULT,'" + board.getSubject(1) + "','" + board.getContent(1) + "','" + staffId + "',DEFAULT,DEFAULT,DEFAULT);";
		int rs = st.executeUpdate(sql);

		if (rs>0) {
			board.setBoardId(1, rs);
			insertBoardChkFlag = true;
		}
		return insertBoardChkFlag;
	}


}
