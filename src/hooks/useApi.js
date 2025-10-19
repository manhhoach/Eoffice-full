import { useEffect, useState } from "react";
import axios from "axios";

export default function useApi(options) {
    const {
        url,
        method = "GET",
        params = {},
        body,
        headers = {},
        auto = true,
        isAuth = true,
        baseUrl = import.meta.env.VITE_API_BASE_URL || "",
    } = options;

    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(auto);
    const [error, setError] = useState(null);

    const fetchData = async (overrideOptions = {}) => {
        setLoading(true);
        setError(null);

        const merged = {
            url,
            method,
            params,
            body,
            headers,
            baseUrl,
            isAuth,
            ...overrideOptions,
        };

        const fullUrl = `${merged.baseUrl}${merged.url}`;
        const isFormData = merged.body instanceof FormData;
        const token = merged.isAuth ? localStorage.getItem("accessToken") : null;

        const axiosConfig = {
            url: fullUrl,
            method: merged.method,
            params: merged.params,
            data: merged.body,
            headers: {
                ...(isFormData ? {} : { "Content-Type": "application/json" }),
                ...(token ? { Authorization: `Bearer ${token}` } : {}),
                ...merged.headers,
            },
        };

        try {
            const res = await axios(axiosConfig);
            setData(res.data);
            return res.data;
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (auto) fetchData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [url, JSON.stringify(params)]);

    return { data, loading, error, refetch: fetchData };
}
