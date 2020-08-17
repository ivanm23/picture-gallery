(ns picture-gallery.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [picture-gallery.handler :refer :all]
            [buddy.hashers :as hashers]
            [cheshire.core :refer [parse-string]]))

(defn encode-auth [user pass]
  (->> (str user ":" pass)
       (.getBytes)
       (.encodeToString (java.util.Base64/getEncoder))
       (str "Basic ")))

(defn parse-response [body]
   (-> body slurp (parse-string true)))

(defn mock-get-user [{:keys [id]}]
  (if (= id "foo")
    {:id "foo"
     :pass (hashers/encrypt "bar")}))

(defn login-request [id pass]
  (-> (request :post "/login")
      (header "Authorization" (encode-auth id pass))))

(deftest test-login
  (testing "login success"
    (with-redefs [picture-gallery.db.core/get-user mock-get-user]
      (let [{:keys [body status]} (app (login-request "foo" "bar"))]
        (is
         (= 200 status))
        (is
         (= {:result "ok"}
            (parse-response body))))))

  (testing "password mismatch"
    (with-redefs [picture-gallery.db.core/get-user mock-get-user]
      (let [{:keys [body status]} (app (login-request "foo" "baz"))]
        (is
         (= {:result "unauthorized" :message "login failure"}
            (parse-response body)))
        (is
         (= 401 status))))))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= 404 (:status response))))))
