(ns sin-wave.core)
(defn on-js-reload [])

(defn canvas []
  (.getElementById js/document "myCanvas"))

(defn ctx []
  (.getContext (canvas) "2d"))

(def rx-interval js/rxjs.interval)
(def rx-take js/rxjs.operators.take)
(def rx-map js/rxjs.operators.map)
(def app-time (rx-interval 10))

(defn deg-to-rad [n] 
  (* (/ Math/PI 180) n))

(defn sine-coord [x] 
  (let [sin (Math/sin (deg-to-rad x)) 
        y   (- 100 (* sin 90))] 
    {:x   x 
     :y   y 
     :sin sin}))

(def sine-wave
  (.pipe app-time (rx-map sine-coord)))

(defn fill-rect [x y colour]
  (set! (.-fillStyle (ctx)) colour)
  (.fillRect (ctx) x y 2 2))

(-> app-time
    (.pipe (rx-take 700))
    (.subscribe (fn [num]
                  (fill-rect x y "orange"))))
