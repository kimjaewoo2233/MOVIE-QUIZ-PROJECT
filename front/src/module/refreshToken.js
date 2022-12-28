import axios from "axios";
import {
    getAccessToken,
    getCookieToken,
    setAccessToken,
    setRefreshToken,
} from "../storage/Cookie";

const newAccessToken = (err) => {
    const data = JSON.stringify({
        accessToken: getAccessToken(),
        refreshToken: getCookieToken(),
    });

    const config = {
        method: "post",
        url: "/refreshToken",
        headers: {
            "Content-Type": "application/json",
        },
        data: data,
    };

    if (err.response.status === 403) {
        axios(config).then((res) => {
            setAccessToken(res.data.accessToken);
            setRefreshToken(res.data.refreshToken);
            window.location.reload();
        });
    }
};

export { newAccessToken };
